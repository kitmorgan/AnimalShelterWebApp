package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.techelevator.model.UserNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.techelevator.model.User;

//@PreAuthorize("isAuthenticated()")
@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }
        return userId;
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			throw new UserNotFoundException();
		}
	}

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Override
    public boolean create(String username, String password, String role) {
        return create(username, password, role, false);
    }

    @Override
    public boolean create(String username, String password, String role, boolean passwordNeedsChanged) {
        String insertUserSql = "insert into users (username,password_hash,role,password_reset) values (?,?,?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        return jdbcTemplate.update(insertUserSql, username, password_hash, ssRole, passwordNeedsChanged) == 1;
    }

    @Override
    public int changePassword(String changedPassword, int user_id) {
        String changedPasswordSql =
                "update users set password_hash = ?, password_reset = false where user_id = ?;";
        String password_hash = new BCryptPasswordEncoder().encode(changedPassword);
        return jdbcTemplate.update(changedPasswordSql, password_hash, user_id);
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setPasswordNeedsChanged(rs.getBoolean("password_reset"));
        user.setActivated(true);
        return user;
    }
}

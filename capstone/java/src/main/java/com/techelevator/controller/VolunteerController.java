package com.techelevator.controller;

import com.techelevator.dao.VolunteerDao;
import com.techelevator.model.Volunteer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class VolunteerController {

    private VolunteerDao volunteerDao;

    public VolunteerController(VolunteerDao volunteerDao) {
        this.volunteerDao = volunteerDao;
    }

    @GetMapping(path = "/volunteers")
    public List<Volunteer> findAll() {
        return volunteerDao.findAll();
    }

    @GetMapping(path = "/volunteers/{volunteer_id}")
    public Volunteer findById(@PathVariable int volunteer_id) {
        return volunteerDao.findById(volunteer_id);
    }

    @GetMapping(path = "/volunteers/name/{full_name}")
    public Volunteer findByName(@PathVariable  String full_name) {
        return volunteerDao.findByName(full_name);
    }

    @GetMapping(path = "/volunteers/email/{email}")
    public Volunteer findByEmail(@PathVariable String email) {
        return volunteerDao.findByEmail(email);
    }

    @GetMapping(path = "/volunteers/user/{user_id}")
    public Volunteer findByUserId(@PathVariable int user_id) {
        return volunteerDao.findByUserId(user_id);
    }

    @GetMapping(path = "/volunteers/reference/{volunteer_id}")
    public Volunteer findReferenceByVolunteer(@PathVariable int volunteer_id) {
        return volunteerDao.findReferenceByVolunteer(volunteer_id);
    }

    @PostMapping(path = "/volunteers/submit")
    public boolean postVolunteerSubmission(@RequestBody Volunteer newVolunteer) {
        return volunteerDao.postVolunteerSubmission(newVolunteer);
    }


}
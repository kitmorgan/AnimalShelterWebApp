<template>
  <div class="card">
    <!-- Cover -->
    <div class="card__cover">
      <img :src="photo.photo_link" alt="" />
    </div>

    <!-- Content -->
    <div class="card__content">
      <div class="petname">
        {{ animal.name }}
      </div>
      <br />
      <div class="petinfo">
        {{ animal.age }} Years Old<br />
        {{ animalType }} - {{ animal.breed }}<br />
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["animal", "photo"],
  computed: {
    animalType() {
      if (this.animal.animal_type_id == 1) {
        return "Dog";
      } else if (this.animal.animal_type_id == 2) {
        return "Cat";
      } else if (this.animal.animal_type_id == 3) {
        return "Farm Animal";
      } else {
        return "Small Animal";
      }
    },
  },
};
</script>

<style scoped>
.card {
  display: flex;
  flex-direction: column;
  border: solid 1px black;
  max-width: 200px;
  color: aliceblue;
  /* border-radius: 8px; */
  margin-bottom: 20px;
  cursor: pointer;
  box-shadow: rgba(50, 50, 93, 0.25) 0px 6px 12px -2px,
    rgba(0, 0, 0, 0.3) 0px 3px 7px -3px;
}

.card__content {
  /* Take available height */
  flex: 1;
  margin: 0px;
  padding-bottom: 5px;
  margin-top: 0px;
  color: black;
  padding-left: 10px;
  font-family: Calibri, Candara, Segoe, "Segoe UI", Optima, Arial, sans-serif;
}

.card:hover {
  opacity: 0.5;
}

img {
  width: 180px;
  border-radius: 10px;
  height: 150px;
  object-fit: cover;
  padding: 7px;
}

.petinfo {
  font-size: 0.9em;
}

.petname {
  font-weight: bold;
}

@media screen and (max-width: 450px) {
  .card {
    max-width: 150px;
    margin: 0px 20px 0px 10px;
  }

  img {
    width: 135px;
  }
}
</style>
package com.example.racekat_ny_udgave.Infrastructure.Repositories;

import com.example.racekat_ny_udgave.Model.Pet;
import com.example.racekat_ny_udgave.Model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class PetRepo implements PetRepoInt {

    private JdbcTemplate jdbcTemplate;

    public PetRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Pet> getAllPets(){
        String sql = "SELECT pet_id AS petId, NAME AS petName, age AS petAge, BREED AS breed, owner_id AS profileId FROM pets";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pet.class));
    }



    @Override
    public Pet addPet(Pet pet) {
        String sql = "INSERT INTO pets (NAME, BREED, age, owner_id) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsInserted = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"pet_id"});
            ps.setString(1, pet.getPetName());
            ps.setString(2, pet.getBreed());
            ps.setInt(3, pet.getPetAge());
            ps.setInt(4, pet.getProfileId());
            return ps;
        }, keyHolder);


        System.out.println("Rows inserted in pets: " + rowsInserted);
        System.out.println("Generated pet_id: " + keyHolder.getKey());
        pet.setPetId(keyHolder.getKey().intValue());
        return pet;
    }

    @Override
    public void deletePet(Pet pet){
    String sql = "DELETE FROM pets WHERE pet_id = ?";
    jdbcTemplate.update(sql, pet.getPetId());
    }

    @Override
    public Pet findPetById(int petId) {
        String sql = "SELECT pet_id AS petId, NAME AS petName, age AS petAge, BREED AS breed, owner_id AS profileId FROM pets WHERE pet_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pet.class), petId);
    }


    @Override
    public void updatePet(Pet pet){
    String sql = "UPDATE pets SET name = ?, age = ?, breed = ? WHERE pet_id = ?";
    jdbcTemplate.update(sql, pet.getPetName(), pet.getPetAge(), pet.getBreed(), pet.getPetId());
    }

    @Override
    public Pet findPetByOwner(User user){ //Skal ændres til profile efter implementationen af adskillelse mellem user og profile
    String sql = "SELECT * FROM pets WHERE owner_Id = ?"; //relationer kræver user - profile - pet
    return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pet.class), user.getUserId());
    }
}

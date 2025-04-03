package com.example.racekat_ny_udgave.Infrastructure.Repositories;

import com.example.racekat_ny_udgave.Model.Pet;
import com.example.racekat_ny_udgave.Model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PetRepo implements PetRepoInt {

    private JdbcTemplate jdbcTemplate;

    public PetRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Pet> getAllPets(){
    String sql = "SELECT * FROM pets";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Pet.class));
    }

    @Override
    public Pet addPet(Pet pet){
        String sql = "INSERT INTO pets (name, breed, age, owner_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, pet.getPetName(), pet.getBreed(), pet.getPetAge(), pet.getOwnerId());
        return pet;
    }


    @Override
    public void deletePet(Pet pet){
    String sql = "DELETE FROM pets WHERE id = ?";
    jdbcTemplate.update(sql, pet.getPetId());
    }

    @Override
    public Pet findPetById(int petId){
        String sql = "SELECT * FROM pets WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pet.class), petId);
    }

    @Override
    public void updatePet(Pet pet){
    String sql = "UPDATE pets SET name = ?, age = ?, breed = ? WHERE id = ?";
    jdbcTemplate.update(sql, pet.getPetName(), pet.getPetAge(), pet.getBreed(), pet.getPetId());
    }

    @Override
    public Pet findPetByOwner(User user){
    String sql = "SELECT * FROM pets WHERE owner = ?";
    return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Pet.class), user.getUserId());
    }
}

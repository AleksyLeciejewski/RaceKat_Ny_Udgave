package com.example.racekat_ny_udgave.Infrastructure.Repositories;

import com.example.racekat_ny_udgave.Model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileRepo implements ProfileRepoInt {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProfileRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Profile getProfileByUserId(int userId) {
        String sql = "SELECT * FROM Profile WHERE profile_userfk = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Profile.class), userId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Profile getProfileById(int profileId) {
        String sql = "SELECT * FROM Profile WHERE profile_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Profile.class), profileId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Profile createProfile(Profile profile, int userId) {
        String sql = "INSERT INTO profile (profile_name, profile_description, profile_userfk) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, profile.getProfileName(), profile.getProfileDescription(), userId);
        return getProfileByUserId(userId);
    }

    @Override
    public void updateProfile(Profile profile) {
        String sql = "UPDATE Profile SET profile_name = ?, profile_description = ? WHERE profile_id = ?";
        jdbcTemplate.update(sql, profile.getProfileName(), profile.getProfileDescription(), profile.getProfileId());
    }

    @Override
    public void deleteProfile(int profileId) {
        String sql = "DELETE FROM Profile WHERE profile_id = ?";
        jdbcTemplate.update(sql, profileId);
    }

    @Override
    public List<Profile> getAllProfiles() {
        String sql = "SELECT * FROM Profile";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class));
    }
}
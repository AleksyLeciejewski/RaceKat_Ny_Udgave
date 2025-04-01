package com.example.racekat_ny_udgave.Infrastructure.Repositories;

import com.example.racekat_ny_udgave.Model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserRepo implements UserRepoInt{

    private JdbcTemplate jdbcTemplate;

    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers(){
    String sql = "SELECT * FROM owners";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User addUser(User user) {
    String sql = "INSERT INTO owners (username, name, id, email) VALUES (?, ?, ?, ?)";
    jdbcTemplate.update(sql, user.getUsername(), user.getOwnerName(), user.getUserId(), user.getEmail());
    return user;
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM owners WHERE id = ?";
        jdbcTemplate.update(sql, user.getUserId());
    }


    @Override
    public User getUser(int userId){
        String sql = "SELECT * FROM owners WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Override
    public void updateUser(User user){
    String sql = "UPDATE owners SET name = ?, id = ?, email = ? WHERE id = ?";
    jdbcTemplate.update(sql, user.getOwnerName(), user.getUserId(), user.getEmail(), user.getUserId());
    }
}

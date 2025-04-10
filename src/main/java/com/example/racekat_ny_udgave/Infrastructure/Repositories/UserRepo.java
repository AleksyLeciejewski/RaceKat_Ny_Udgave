package com.example.racekat_ny_udgave.Infrastructure.Repositories;

import com.example.racekat_ny_udgave.Model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class UserRepo implements UserRepoInt {

    private JdbcTemplate jdbcTemplate;

    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM bruger";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO bruger (email, name, password) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"bruger_id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getOwnerName());
            ps.setString(3, user.getPassword());
            return ps;
        }, keyHolder);

        user.setUserId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public void deleteUser(User user) {
        String sql = "DELETE FROM bruger WHERE bruger_id = ?";
        jdbcTemplate.update(sql, user.getUserId());
    }


    @Override
    public User getUser(int userId) {
        String sql = "SELECT bruger_id AS userId, email, name AS ownerName, password FROM bruger WHERE bruger_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userId);
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE bruger SET name = ?, bruger_id = ?, email = ? WHERE bruger_id = ?";
        jdbcTemplate.update(sql, user.getOwnerName(), user.getUserId(), user.getEmail(), user.getUserId());
    }

    @Override
    public User findUserByEmail(String email) {
        try {
            String sql = "SELECT bruger_id AS userId, email, name AS ownerName, password FROM bruger WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}

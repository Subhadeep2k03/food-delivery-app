package com.fooddelivery.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.User;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // converts one database row into a User object
    private final RowMapper<User> mapper = (rs, rowNum) -> {
        User u = new User();
        u.setId(rs.getLong("id"));
        u.setName(rs.getString("name"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setRole(rs.getString("role"));
        return u;
    };

    public void save(User user) {
        // id is not inserted: the Oracle trigger fills it from the sequence
        jdbcTemplate.update(
            "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)",
            user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    }

    public User findByEmail(String email) {
        List<User> list = jdbcTemplate.query(
            "SELECT id, name, email, password, role FROM users WHERE email = ?",
            mapper, email);
        return list.isEmpty() ? null : list.get(0);
    }
}
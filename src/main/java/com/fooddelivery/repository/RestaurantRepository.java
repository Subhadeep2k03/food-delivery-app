package com.fooddelivery.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.Restaurant;

@Repository
public class RestaurantRepository {

    private final JdbcTemplate jdbcTemplate;

    public RestaurantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Restaurant> mapper = (rs, rowNum) -> {
        Restaurant r = new Restaurant();
        r.setId(rs.getLong("id"));
        r.setName(rs.getString("name"));
        r.setAddress(rs.getString("address"));
        r.setCuisine(rs.getString("cuisine"));
        return r;
    };

    public List<Restaurant> findAll() {
        return jdbcTemplate.query(
            "SELECT id, name, address, cuisine FROM restaurants ORDER BY name", mapper);
    }

    public Restaurant findById(Long id) {
        List<Restaurant> list = jdbcTemplate.query(
            "SELECT id, name, address, cuisine FROM restaurants WHERE id = ?", mapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public void save(Restaurant r) {
        jdbcTemplate.update(
            "INSERT INTO restaurants (name, address, cuisine) VALUES (?, ?, ?)",
            r.getName(), r.getAddress(), r.getCuisine());
    }
}
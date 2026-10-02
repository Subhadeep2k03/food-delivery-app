package com.fooddelivery.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.FoodItem;

@Repository
public class FoodItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public FoodItemRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<FoodItem> mapper = (rs, rowNum) -> {
        FoodItem f = new FoodItem();
        f.setId(rs.getLong("id"));
        f.setRestaurantId(rs.getLong("restaurant_id"));
        f.setName(rs.getString("name"));
        f.setPrice(rs.getBigDecimal("price"));
        f.setAvailable(rs.getInt("available") == 1);
        return f;
    };

    // only available items (for customers)
    public List<FoodItem> findAvailableByRestaurant(Long restaurantId) {
        return jdbcTemplate.query(
            "SELECT id, restaurant_id, name, price, available FROM food_items "
          + "WHERE restaurant_id = ? AND available = 1 ORDER BY name",
            mapper, restaurantId);
    }

    // all items, including hidden ones (for admin)
    public List<FoodItem> findAllByRestaurant(Long restaurantId) {
        return jdbcTemplate.query(
            "SELECT id, restaurant_id, name, price, available FROM food_items "
          + "WHERE restaurant_id = ? ORDER BY name",
            mapper, restaurantId);
    }

    public FoodItem findById(Long id) {
        List<FoodItem> list = jdbcTemplate.query(
            "SELECT id, restaurant_id, name, price, available FROM food_items WHERE id = ?",
            mapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public void save(FoodItem item) {
        jdbcTemplate.update(
            "INSERT INTO food_items (restaurant_id, name, price) VALUES (?, ?, ?)",
            item.getRestaurantId(), item.getName(), item.getPrice());
    }

    public void toggleAvailable(Long id) {
        jdbcTemplate.update("UPDATE food_items SET available = 1 - available WHERE id = ?", id);
    }
}
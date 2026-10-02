package com.fooddelivery.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;

@Repository
public class OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public OrderRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Order> orderMapper = (rs, rowNum) -> {
        Order o = new Order();
        o.setId(rs.getLong("id"));
        o.setUserId(rs.getLong("user_id"));
        o.setTotal(rs.getBigDecimal("total"));
        o.setStatus(rs.getString("status"));
        o.setCreatedAt(rs.getTimestamp("created_at"));
        return o;
    };

    private final RowMapper<Order> adminOrderMapper = (rs, rowNum) -> {
        Order o = new Order();
        o.setId(rs.getLong("id"));
        o.setUserId(rs.getLong("user_id"));
        o.setUserName(rs.getString("user_name"));
        o.setTotal(rs.getBigDecimal("total"));
        o.setStatus(rs.getString("status"));
        o.setCreatedAt(rs.getTimestamp("created_at"));
        return o;
    };

    private final RowMapper<OrderItem> itemMapper = (rs, rowNum) -> {
        OrderItem i = new OrderItem();
        i.setFoodItemId(rs.getLong("food_item_id"));
        i.setName(rs.getString("name"));
        i.setQuantity(rs.getInt("quantity"));
        i.setPrice(rs.getBigDecimal("price"));
        return i;
    };

    public Long nextOrderId() {
        return jdbcTemplate.queryForObject("SELECT orders_seq.NEXTVAL FROM dual", Long.class);
    }

    public void insertOrder(Long id, Long userId, BigDecimal total) {
        jdbcTemplate.update(
            "INSERT INTO orders (id, user_id, total) VALUES (?, ?, ?)", id, userId, total);
    }

    public void insertItem(Long orderId, OrderItem item) {
        jdbcTemplate.update(
            "INSERT INTO order_items (order_id, food_item_id, quantity, price) VALUES (?, ?, ?, ?)",
            orderId, item.getFoodItemId(), item.getQuantity(), item.getPrice());
    }

    public List<Order> findByUser(Long userId) {
        return jdbcTemplate.query(
            "SELECT id, user_id, total, status, created_at FROM orders "
          + "WHERE user_id = ? ORDER BY id DESC",
            orderMapper, userId);
    }

    public List<Order> findAll() {
        return jdbcTemplate.query(
            "SELECT o.id, o.user_id, u.name AS user_name, o.total, o.status, o.created_at "
          + "FROM orders o JOIN users u ON u.id = o.user_id ORDER BY o.id DESC",
            adminOrderMapper);
    }

    public List<OrderItem> findItems(Long orderId) {
        return jdbcTemplate.query(
            "SELECT oi.food_item_id, f.name, oi.quantity, oi.price "
          + "FROM order_items oi JOIN food_items f ON f.id = oi.food_item_id "
          + "WHERE oi.order_id = ?",
            itemMapper, orderId);
    }

    public void updateStatus(Long orderId, String status) {
        jdbcTemplate.update("UPDATE orders SET status = ? WHERE id = ?", status, orderId);
    }
}
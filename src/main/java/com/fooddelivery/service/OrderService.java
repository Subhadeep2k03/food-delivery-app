package com.fooddelivery.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fooddelivery.model.CartItem;
import com.fooddelivery.model.FoodItem;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.OrderItem;
import com.fooddelivery.repository.FoodItemRepository;
import com.fooddelivery.repository.OrderRepository;

@Service
public class OrderService {

    public static final List<String> STATUSES =
        List.of("PLACED", "PREPARING", "DELIVERED", "CANCELLED");

    private final OrderRepository orderRepository;
    private final FoodItemRepository foodItemRepository;

    public OrderService(OrderRepository orderRepository, FoodItemRepository foodItemRepository) {
        this.orderRepository = orderRepository;
        this.foodItemRepository = foodItemRepository;
    }

    @Transactional
    public Long placeOrder(Long userId, Collection<CartItem> cartItems) {
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Your cart is empty");
        }

        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> lines = new ArrayList<>();

        for (CartItem c : cartItems) {
            FoodItem food = foodItemRepository.findById(c.getFoodItemId());
            if (food == null || !food.isAvailable()) {
                throw new IllegalStateException(c.getName() + " is no longer available");
            }
            OrderItem line = new OrderItem();
            line.setFoodItemId(food.getId());
            line.setName(food.getName());
            line.setQuantity(c.getQuantity());
            line.setPrice(food.getPrice());
            lines.add(line);
            total = total.add(line.getSubtotal());
        }

        Long orderId = orderRepository.nextOrderId();
        orderRepository.insertOrder(orderId, userId, total);
        for (OrderItem line : lines) {
            orderRepository.insertItem(orderId, line);
        }
        return orderId;
    }

    public List<Order> getOrdersForUser(Long userId) {
        List<Order> orders = orderRepository.findByUser(userId);
        for (Order o : orders) {
            o.setItems(orderRepository.findItems(o.getId()));
        }
        return orders;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        for (Order o : orders) {
            o.setItems(orderRepository.findItems(o.getId()));
        }
        return orders;
    }

    public void updateStatus(Long orderId, String status) {
        if (!STATUSES.contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        orderRepository.updateStatus(orderId, status);
    }
}
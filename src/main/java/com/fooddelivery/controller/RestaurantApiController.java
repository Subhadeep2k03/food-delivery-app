package com.fooddelivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.model.FoodItem;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    public RestaurantApiController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getOne(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<FoodItem>> getMenu(@PathVariable Long id) {
        if (restaurantService.getRestaurant(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurantService.getMenu(id));
    }
}
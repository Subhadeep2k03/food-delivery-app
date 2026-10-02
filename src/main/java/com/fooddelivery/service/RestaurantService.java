package com.fooddelivery.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fooddelivery.model.FoodItem;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.repository.FoodItemRepository;
import com.fooddelivery.repository.RestaurantRepository;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final FoodItemRepository foodItemRepository;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             FoodItemRepository foodItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.foodItemRepository = foodItemRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurant(Long id) {
        return restaurantRepository.findById(id);
    }

    public List<FoodItem> getMenu(Long restaurantId) {
        return foodItemRepository.findAvailableByRestaurant(restaurantId);
    }

    public void addRestaurant(String name, String address, String cuisine) {
        Restaurant r = new Restaurant();
        r.setName(name.trim());
        r.setAddress(address.trim());
        r.setCuisine(cuisine.trim());
        restaurantRepository.save(r);
    }

    public List<FoodItem> getAllItems(Long restaurantId) {
        return foodItemRepository.findAllByRestaurant(restaurantId);
    }

    public void addFoodItem(Long restaurantId, String name, BigDecimal price) {
        FoodItem item = new FoodItem();
        item.setRestaurantId(restaurantId);
        item.setName(name.trim());
        item.setPrice(price);
        foodItemRepository.save(item);
    }

    public void toggleItem(Long itemId) {
        foodItemRepository.toggleAvailable(itemId);
    }

    public FoodItem getItem(Long id) {
        return foodItemRepository.findById(id);
    }
}
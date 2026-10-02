package com.fooddelivery.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fooddelivery.model.Restaurant;
import com.fooddelivery.model.User;
import com.fooddelivery.service.RestaurantService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    private final RestaurantService restaurantService;

    public AdminController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return user != null && "ADMIN".equals(user.getRole());
    }

    @GetMapping("/admin/restaurants")
    public String restaurants(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        return "admin-restaurants";
    }

    @PostMapping("/admin/restaurants")
    public String addRestaurant(@RequestParam String name,
                                @RequestParam String address,
                                @RequestParam String cuisine,
                                HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        restaurantService.addRestaurant(name, address, cuisine);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/admin/restaurants/{id}/items")
    public String items(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) return "redirect:/admin/restaurants";
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("items", restaurantService.getAllItems(id));
        return "admin-items";
    }

    @PostMapping("/admin/restaurants/{id}/items")
    public String addItem(@PathVariable Long id,
                          @RequestParam String name,
                          @RequestParam BigDecimal price,
                          HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        restaurantService.addFoodItem(id, name, price);
        return "redirect:/admin/restaurants/" + id + "/items";
    }

    @PostMapping("/admin/items/{itemId}/toggle")
    public String toggle(@PathVariable Long itemId,
                         @RequestParam Long restaurantId,
                         HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        restaurantService.toggleItem(itemId);
        return "redirect:/admin/restaurants/" + restaurantId + "/items";
    }
}
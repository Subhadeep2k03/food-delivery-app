package com.fooddelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fooddelivery.model.Restaurant;
import com.fooddelivery.service.RestaurantService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public String restaurants(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        model.addAttribute("restaurants", restaurantService.getAllRestaurants());
        return "restaurants";
    }

    @GetMapping("/restaurants/{id}")
    public String menu(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }
        Restaurant restaurant = restaurantService.getRestaurant(id);
        if (restaurant == null) {
            return "redirect:/restaurants";
        }
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("items", restaurantService.getMenu(id));
        return "menu";
    }
}
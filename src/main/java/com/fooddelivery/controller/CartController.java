package com.fooddelivery.controller;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fooddelivery.model.CartItem;
import com.fooddelivery.model.FoodItem;
import com.fooddelivery.service.RestaurantService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    private final RestaurantService restaurantService;

    public CartController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @SuppressWarnings("unchecked")
    private Map<Long, CartItem> getCart(HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        Map<Long, CartItem> cart = getCart(session);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem c : cart.values()) {
            total = total.add(c.getSubtotal());
        }
        model.addAttribute("items", cart.values());
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String add(@RequestParam Long foodItemId,
                      @RequestParam(defaultValue = "1") int quantity,
                      HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";

        FoodItem food = restaurantService.getItem(foodItemId);
        if (food == null || !food.isAvailable()) return "redirect:/restaurants";
        if (quantity < 1) quantity = 1;
        if (quantity > 20) quantity = 20;

        Map<Long, CartItem> cart = getCart(session);

        // a cart can hold items from one restaurant only
        for (CartItem c : cart.values()) {
            if (!c.getRestaurantId().equals(food.getRestaurantId())) {
                return "redirect:/cart?otherRestaurant";
            }
        }

        CartItem existing = cart.get(foodItemId);
        if (existing != null) {
            existing.setQuantity(Math.min(existing.getQuantity() + quantity, 20));
        } else {
            CartItem c = new CartItem();
            c.setFoodItemId(food.getId());
            c.setRestaurantId(food.getRestaurantId());
            c.setName(food.getName());
            c.setPrice(food.getPrice());
            c.setQuantity(quantity);
            cart.put(foodItemId, c);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove")
    public String remove(@RequestParam Long foodItemId, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";
        getCart(session).remove(foodItemId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/clear")
    public String clear(HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) return "redirect:/login";
        getCart(session).clear();
        return "redirect:/cart";
    }
}
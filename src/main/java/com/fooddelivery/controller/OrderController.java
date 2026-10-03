package com.fooddelivery.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fooddelivery.model.CartItem;
import com.fooddelivery.model.User;
import com.fooddelivery.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/orders/place")
    public String place(HttpSession session, RedirectAttributes redirect) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirect.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        try {
            orderService.placeOrder(user.getId(), cart.values());
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("error", e.getMessage());
            return "redirect:/cart";
        }

        cart.clear();
        return "redirect:/orders?placed";
    }

    @GetMapping("/orders")
    public String myOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("orders", orderService.getOrdersForUser(user.getId()));
        return "orders";
    }

    @PostMapping("/orders/{id}/cancel")
    public String cancel(@PathVariable Long id,
                         HttpSession session,
                         RedirectAttributes redirect) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        if (orderService.cancelOrder(id, user.getId())) {
            return "redirect:/orders?cancelled";
        }
        redirect.addFlashAttribute("error",
            "This order can no longer be cancelled. It may already be in preparation.");
        return "redirect:/orders";
    }
}
package com.fooddelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fooddelivery.model.User;
import com.fooddelivery.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminOrderController {

    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return user != null && "ADMIN".equals(user.getRole());
    }

    @GetMapping("/admin/orders")
    public String orders(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("statuses", OrderService.STATUSES);
        return "admin-orders";
    }

    @PostMapping("/admin/orders/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }
}
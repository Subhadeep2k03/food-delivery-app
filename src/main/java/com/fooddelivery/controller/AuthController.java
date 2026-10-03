package com.fooddelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fooddelivery.model.User;
import com.fooddelivery.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        String error = userService.register(name, email, password);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("name", name);    // keep what the user typed
            model.addAttribute("email", email);
            return "register";
        }
        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.login(email, password);
        if (user == null) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
        user.setPassword(null);
        session.setAttribute("loggedInUser", user);
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
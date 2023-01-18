package com.example.controller;

import org.springframework.ui.Model;
import com.example.entity.User;
import com.example.service.UserNotFoundException;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
    @Autowired
    private UserService service;

    @GetMapping("")
    public String showHomePage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login/request")
    public String login(Model model, HttpSession session, User user, RedirectAttributes ra){
        try {
            User userFind = service.login(user.getEmail(), user.getPassword());
            session.setAttribute("curUser", userFind);
            ra.addFlashAttribute("message", "Login Successful");
            return "redirect:/users";
        } catch (UserNotFoundException e) {
            //ra.addFlashAttribute("message",e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model){
        session.invalidate();
        model.addAttribute("user", new User());
        return "login";
    }
}

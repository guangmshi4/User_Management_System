package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String showUserList(Model model){
        List<User> listUsers = userService.listAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/users/new")
    public String showNewForm(Model model){
        model.addAttribute("pageTitle", "Add New User");
        model.addAttribute("user", new User());
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra){
        userService.save(user);
        ra.addFlashAttribute("message", "The user has been saved successfully!");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            User user = userService.get(id);
            model.addAttribute("pageTitle", "Edit User id: " + id);
            model.addAttribute("user", user);
            return "user_form";
        } catch (UserNotFound e) {
            ra.addFlashAttribute("message", "User with id " + id + " not found");
            return "redirect:/users";
        }

    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
            userService.delete(id);
            ra.addFlashAttribute("message", "The user with id: " + id + " has been deleted successfully!");
        } catch (UserNotFound e) {
            ra.addFlashAttribute("message", "User with id " + id + " not found");
        }
        return "redirect:/users";
    }
}

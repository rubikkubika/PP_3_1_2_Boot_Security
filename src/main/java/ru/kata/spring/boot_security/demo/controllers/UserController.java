package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;


@Controller
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String getAllUser(ModelMap model, Principal principal) {
        model.addAttribute("loggeduser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("roles", userService.allRoles());
        return "users";
    }

    @GetMapping(value = "/user")
    public String checkUser(ModelMap model, Principal principal) {
        model.addAttribute("user", userService.getUserByUsername(principal.getName()));
        return "user";
    }

    @GetMapping(value = "/")

    public String welcome(ModelMap model, Principal principal) {
        model.addAttribute("loggeduser", userService.getUserByUsername(principal.getName()));
        model.addAttribute("users", userService.getAllUser());
        model.addAttribute("roles", userService.allRoles());
        return "users";
    }


    @GetMapping(value = "/newuser")
    public String createUser(ModelMap modelMap, Principal principal) {
        modelMap.addAttribute("user", new User());
        modelMap.addAttribute("roles", userService.allRoles());
        modelMap.addAttribute("loggeduser", userService.getUserByUsername(principal.getName()));
        return "newuser";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles1") String[] roles) {
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(ModelMap modelMap, @PathVariable("id") long id) {
        modelMap.addAttribute("user", userService.getUserById(id));
        modelMap.addAttribute("roles", userService.allRoles());
        return "editUser";
    }

    @PatchMapping("/admin")
    public String update(User user,
                         @RequestParam(value = "roles1") String[] roles) {

        userService.update(user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteByID(id);
        return "redirect:/admin";
    }
}

package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;


@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users/index";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/users/";
    }

    @GetMapping("/showIdFor3")
    public String showUserByIdForm(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id != null) {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("id", id);
        }
        return "users/showId";
    }

    @GetMapping(value = "/show", params = "id")
    public String showUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/show";
    }

    @GetMapping(value = "/delete", params = "id")
    public String showDeleteUserForm(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
        userService.removeUserById(id);
        return "redirect:/users/";
    }

    @GetMapping(value = "/update")
    public String showUpdateForm(@RequestParam(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users/update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/users/";
    }
}

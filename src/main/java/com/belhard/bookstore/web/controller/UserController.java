package com.belhard.bookstore.web.controller;

import com.belhard.bookstore.service.UserService;
import com.belhard.bookstore.service.dto.UserDto;
import com.belhard.bookstore.service.dto.UserDtoLogin;
import com.belhard.bookstore.service.dto.UserDtoWithoutPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        UserDtoWithoutPassword user = service.getById(id);
        model.addAttribute("user", user);
        return "user/user";
    }

    @GetMapping("/all")
    public String getUsers(Model model) {
        List<UserDtoWithoutPassword> users = service.getAll();
        model.addAttribute("users", users);
        return "user/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        service.delete(id);
        return "user/userDelete";
    }

    @GetMapping("/create")
    public String createUserForm() {
        return "user/userCreateForm";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute UserDtoLogin userDtoLogin) {
        UserDtoLogin user = service.create(userDtoLogin);
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        UserDtoWithoutPassword user = service.getById(id);
        model.addAttribute("user", user);
        return "user/userEdit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@ModelAttribute UserDto userDto) {
        service.update(userDto);
        return "redirect:/users/" + userDto.getId();
    }

    @GetMapping("/{id}/orders")
    public String editUserForm(@PathVariable Long id) {
        return "redirect:/orders/user/" + id;
    }

}

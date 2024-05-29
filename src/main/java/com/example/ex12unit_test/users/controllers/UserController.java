package com.example.ex12unit_test.users.controllers;

import com.example.ex12unit_test.users.models.User;
import com.example.ex12unit_test.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody User user) {
    return userService.create(user);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}")
    public Optional<User> loadBy(@PathVariable long id) {
        return userService.getBy(id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping
    public Collection<User> loadAll(){
        return userService.getAllUsers();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{id}")
    public void deleteBy(@PathVariable long id) {
        userService.delete(id);
    }
}

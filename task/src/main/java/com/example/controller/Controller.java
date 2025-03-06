package com.example.controller;

import com.example.User.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class Controller {

    @GetMapping("/get")
    public String get() {
        Random random = new Random();
        int delay = 1000 + random.nextInt(1000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "{\"login\":\"Login1\",\"status\":\"ok\"}";

    }

    @PostMapping("/post")
    public String getUserOne(@Valid @RequestBody User user) {
        Random random = new Random();
        int delay = 1000 + random.nextInt(1000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new User(user.getLogin(), user.getPassword()).toString();

    }

}

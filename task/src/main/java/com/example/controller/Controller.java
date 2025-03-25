package com.example.controller;

import com.example.user.DataBaseWorker;
import com.example.user.FileWorker;
import com.example.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class Controller {

    @Autowired
    private DataBaseWorker dataBaseWorker;
    @Autowired
    private FileWorker fileWorker;

    public void delay() {
        Random random = new Random();
        try {
            Thread.sleep(1000 + random.nextInt(1000));
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/selectUser")
    public ResponseEntity<?> selectUser(@RequestParam(value = "login") String login) {
        delay();
        User user = dataBaseWorker.selectUserInDataBase(login);
        if (user == null) return new ResponseEntity<>("User not found", HttpStatus.INTERNAL_SERVER_ERROR);
        fileWorker.writeToFile(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/insertUser")
    public ResponseEntity<?> insertUser(@Valid @RequestBody User user) {
        delay();
        int resultRows = dataBaseWorker.insertUserInDataBase(user);
        if (resultRows == 0) return new ResponseEntity<>("Error update rows", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Number of updated rows: " + resultRows, HttpStatus.OK);
    }

    @GetMapping("/getRandomUser")
    public ResponseEntity<?> getRandomUser() {
        delay();
        return new ResponseEntity<>(fileWorker.readRandomLine(), HttpStatus.OK);
    }
}
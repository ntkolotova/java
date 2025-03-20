package com.example.controller;

import com.example.DataBaseWorker;
import com.example.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Random;

@RestController
public class Controller {

    @GetMapping("/get")
    public ResponseEntity<String> getUser(@RequestParam(value = "login") String login) {
        Random random = new Random();
        int delay = 1000 + random.nextInt(1000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        DataBaseWorker dataBaseWorker = new DataBaseWorker();
        String responseBody;
        try {
            responseBody = String.valueOf((dataBaseWorker.getSelect(login)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Login does not exist", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (SQLException e) {
            return new ResponseEntity<>("Error connecting to DB", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    @PostMapping("/post")
    public ResponseEntity<String> insertUser(@Valid @RequestBody User user) {

        Random random = new Random();
        int delay = 1000 + random.nextInt(1000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        DataBaseWorker dataBaseWorker = new DataBaseWorker();
        String responseBody;
        try {
            responseBody = String.valueOf(dataBaseWorker.getInsert(user));
        } catch (RuntimeException e){
            return new ResponseEntity<>("Invalid JSON", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}

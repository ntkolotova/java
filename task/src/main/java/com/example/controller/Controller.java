package com.example.controller;

import com.example.DataBaseWorker;
import com.example.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Random;

@RestController
public class Controller {

    private final DataBaseWorker dataBaseWorker;
    Random random = new Random();

    @Autowired
    public Controller(DataBaseWorker dataBaseWorker){
        this.dataBaseWorker = dataBaseWorker;
    }

    @GetMapping("/SelectUser")
    public ResponseEntity<?> selectUser(@RequestParam(value = "login") String login) {

        try {
            Thread.sleep(1000 + random.nextInt(1000));
            return new ResponseEntity<>(dataBaseWorker.selectUserInDataBase(login), HttpStatus.OK);
        } catch (InterruptedException e) {
            return new ResponseEntity<>("InterruptedException", HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("SQLException", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/insertUser")
    public ResponseEntity<?> insertUser(@Valid @RequestBody User user) {

        try {
            Thread.sleep(1000 + random.nextInt(1000));
            return new ResponseEntity<>("Number of updated rows: " + dataBaseWorker.insertUserInDataBase(user), HttpStatus.OK);
        } catch (InterruptedException e) {
            return new ResponseEntity<>("InterruptedException", HttpStatus.BAD_REQUEST);
        } catch (SQLException e) {
            return new ResponseEntity<>("SQLException", HttpStatus.BAD_REQUEST);
        }
    }
}
package com.example.controller;

import com.example.template.TemplateGet;
import com.example.template.TemplatePost;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;


@RestController
public class Controller {

    @GetMapping("/get")
    public ResponseEntity<String> get() {
        Random random = new Random();
        int delay = 1000 + random.nextInt(1000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        TemplateGet templateGet = new TemplateGet();
        String responeBody = templateGet.getBody();
        return new ResponseEntity<>(responeBody, HttpStatus.OK);

    }

    @PostMapping("/post")
    public ResponseEntity<String> post(@RequestBody String requestBody) {
        Random random = new Random();
        int delay = 1000 + random.nextInt(1000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper objectMapper = new ObjectMapper();

        String login = "login";
        String password = "password";

        try {
            JsonNode fullJson = objectMapper.readTree(requestBody);
            login = fullJson.get("login").asText();
            password = fullJson.get("password").asText();

        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Не удалось разложить JSON", HttpStatus.BAD_REQUEST);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = LocalDateTime.now().format(formatter);

        TemplatePost template = new TemplatePost();
        String responceBody = template.getBody(login, password, date);

        return new ResponseEntity<>(responceBody, HttpStatus.OK);

    }
}

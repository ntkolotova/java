package com.example.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {

    @NotBlank(message = "Имя не может быть пустым")
    private final String login;
    @NotNull(message = "Пароль не может быть меньше 6 символов")
    @Size(min = 6, message = "Пароль не может быть меньше 6 символов")
    private final String password;
    private final String date;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "{\n" +
                "    \"login\": " + login + "\n" +
                "    \"password\": " + password + "\n" +
                "    \"date\": " + date +
                "\n}";
    }
}

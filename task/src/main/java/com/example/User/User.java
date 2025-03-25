package com.example.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @NotBlank(message = "Имя не может быть пустым")
    private String login;
    @NotBlank(message = "Пароль не может быть меньше 6 символов")
    @Size(min = 6, message = "Пароль не может быть меньше 6 символов")
    private String password;
    private String email;
    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));


    public User(String login, String password, String date, String email) {
        this.login = login;
        this.password = password;
        this.date = date;
        this.email = email;
    }
}
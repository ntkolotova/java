package com.example.template;

public class TemplatePost {
    public String getBody(String login,
                          String password,
                          String date) {
        return String.format("{\n" +
                        "    \"login\": \"%s\",\n" +
                        "    \"password\": \"%s\"\n" +
                        "    \"date\": \"%s\"\n" +
                        "}",
                login,
                password,
                date);
    }
}

package com.example.user;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Component
public class FileWorker {

    private final File file;

    public FileWorker() {

        this.file = new File("./files/selectUser.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write("LOGIN,PASSWORD,EMAIL,DATE");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeToFile(User userFromDataBase) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.newLine();
            writer.write(userFromDataBase.getLogin() + "," +
                    userFromDataBase.getPassword() + "," +
                    userFromDataBase.getEmail() + "," +
                    userFromDataBase.getDate());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String readRandomLine() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("./files/getRandomLine.json"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return lines != null ? lines.get(new Random().nextInt(lines.size())) : null;
    }
}
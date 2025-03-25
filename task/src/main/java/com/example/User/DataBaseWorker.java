package com.example.user;

import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DataBaseWorker {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "admin";
    private static final String PASSWORD = "admin";

    public User selectUserInDataBase(String login){
        Statement statement = null;
        Connection connection_db = null;
        User user = null;
        try {
            connection_db = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            statement = connection_db.createStatement();
            String sql = String.format("SELECT lp.login, lp.password, lp.date, le.email " +
                    "FROM log_pass lp " +
                    "INNER JOIN log_email le on lp.login = le.login " +
                    "WHERE lp.login = '%s'", login);
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                user = new User(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("date"),
                        resultSet.getString("email")
                );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (connection_db != null) {
                try {
                    connection_db.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return user;
    }

    public int insertUserInDataBase(User user){

        String sql = "INSERT INTO log_pass (login, password, date) VALUES (?, ?, ?);" + "\n" +
                "INSERT INTO log_email (login, email) VALUES (?, ?);";
        int resultRows = 0;
        try (Connection connection_db = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             PreparedStatement preparedStatement = connection_db.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getDate());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getEmail());
            resultRows = preparedStatement.executeUpdate() + 1;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return resultRows;
    }
}
package com.example;

import com.example.user.User;

import java.sql.*;

public class DataBaseWorker {

    private static final String URL = "jdbc:postgresql://172.18.0.3:5432/postgres";
    private static final String LOGIN = "admin";
    private static final String PASSWORD = "admin";

    public User getSelect(String login) throws SQLException {
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
            while (resultSet.next()) {
                user = new User(
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("date"),
                        resultSet.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) statement.close();
            if (connection_db != null) connection_db.close();
        }
        if (user == null) throw new RuntimeException();
        return user;
    }

    public int getInsert(User user) {

        String sql = "INSERT INTO log_pass (login, password, date) VALUES (?, ?, ?);" + "\n" +
                "INSERT INTO log_email (login, email) VALUES (?, ?);";
        int resultRows;
        try (Connection connection_db = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             PreparedStatement preparedStatement = connection_db.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getDate());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getEmail());
            resultRows = preparedStatement.executeUpdate() + 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultRows;
    }
}
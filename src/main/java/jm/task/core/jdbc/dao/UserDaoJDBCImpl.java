package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        //Statement statement = null;
        String sql = "CREATE TABLE IF NOT EXISTS users2 " +
                "(ID INTEGER AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30), " +
                "lastName VARCHAR(30), age TINYINT);";
        try (Connection connection = Util.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица user успешно создана");

    }

    public void dropUsersTable() {
        //Statement statement = null;
        String sql = "DROP TABLE IF EXISTS users2;";
        try (Connection connection = Util.getConnection();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Таблица user успешно удалена");
    }


    public void saveUser(String name, String lastName, byte age) {
        // PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO users2(name, lastName, age) VALUES (?, ?, ?);";
        if (name == null || name.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Имя и фамилия не должны быть пустыми.");
        }
        try (Connection connection = Util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeUserById(long id) {
        //PreparedStatement preparedStatement = null;
        String sql = "DELETE FROM users2 WHERE ID = ?;";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Запись успешно удалена");
            } else {
                System.out.println("Не найдено записей для удаления");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении записи: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM users2;";

        try (Connection connection = Util.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                //long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(name, lastName, age);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        if (!allUsers.isEmpty()) {
            for (User user : allUsers) {
                System.out.println(user);
            }
        } else {
            System.out.println("Пользователи не найдены.");
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        //Statement statement = null;
        String sql = "TRUNCATE TABLE users2";
        try (Connection connection = Util.getConnection();
           Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка при очещении таблицы");
        }
    }
}



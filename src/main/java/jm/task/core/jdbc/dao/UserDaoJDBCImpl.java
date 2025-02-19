package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import com.sun.xml.bind.v2.model.core.ID;
import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger logger = Logger.getLogger
            (UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("CREATE TABLE IF NOT EXISTS users2 " +
                             "(ID INTEGER AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30), " +
                             "lastName VARCHAR(30), age TINYINT);")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error of Table creation" + e.getMessage());
        }
        logger.log(Level.SEVERE, "The table has been created");

    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DROP TABLE IF EXISTS users2;")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error of Table delete" + e.getMessage());
        }
        logger.log(Level.SEVERE, "Table deleted");
    }


    public void saveUser(String name, String lastName, byte age) {
        if (name == null || name.isBlank() || lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Name and LastNabe can't be null");
        }
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("INSERT INTO users2(name, lastName, age) VALUES (?, ?, ?);")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error of User save" + e.getMessage());
        }
        logger.log(Level.SEVERE, "User saved");
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("DELETE FROM users2 WHERE ID = ?;")) {
            preparedStatement.setLong(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                logger.log(Level.SEVERE, "User deleted");
            } else {
                logger.log(Level.SEVERE, "Not found Users for delete");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error of User delete" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        String sql = "SELECT * FROM users2;";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("SELECT * FROM users2;")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error of getting list Users" + e.getMessage());
        }
        if (!allUsers.isEmpty()) {
            for (User user : allUsers) {
                System.out.println(user);
            }
        } else {
            logger.log(Level.SEVERE, "Users not founded");
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement
                     ("TRUNCATE TABLE users2")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error of clearing Table" + e.getMessage());
        }
    }
}



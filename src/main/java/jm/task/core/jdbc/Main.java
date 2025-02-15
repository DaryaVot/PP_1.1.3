package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserServiceImpl service = new UserServiceImpl();

        service.saveUser("Ivan", "Ivanov", (byte) 30);
        service.saveUser("Mikhail", "Mikhailov", (byte) 46);
        service.saveUser("Petr", "Petrov", (byte) 25);
        service.saveUser("Jhon", "Jhonson", (byte) 40);

        service.getAllUsers();

        service.removeUserById(2);

        service.cleanUsersTable();

        service.dropUsersTable();

    }
}
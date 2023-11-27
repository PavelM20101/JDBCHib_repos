package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Pavel", "Mordvinov", (byte) 22);
        userService.saveUser("Eugene", "Kalinichev", (byte) 24);
        userService.saveUser("Victor", "Martynov", (byte) 24);
        userService.saveUser("Hazhbulat", "Yiusupasiev", (byte) 22);

        userService.removeUserById(2);

        List<User> userList = userService.getAllUsers();
        System.out.println(userList);

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}

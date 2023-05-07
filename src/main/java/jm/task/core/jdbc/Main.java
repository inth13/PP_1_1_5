package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();

        byte age;

        age = 42;
        userService.saveUser("Gordon", "Freeman", age);
        age = 28;
        userService.saveUser("Alyx", "Vance", age);
        age = 58;
        userService.saveUser("Eli", "Vance", age);
        age = 71;
        userService.saveUser("Isaac", "Kleiner", age);

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Gordon", "Freeman", (byte) 42);
        userService.saveUser("Alyx", "Vance", (byte) 28);
        userService.saveUser("Eli", "Vance", (byte) 58);
        userService.saveUser("Isaac", "Kleiner", (byte) 71);

        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}

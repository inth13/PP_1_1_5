package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String createTable = "create table if not exists user" +
                    "(" +
                    "    id       int auto_increment primary key," +
                    "    name     varchar(255) null," +
                    "    lastName varchar(255) null," +
                    "    age      int(3)       null" +
                    ")";
            statement.executeUpdate(createTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String dropTable = "drop table if exists user";
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String insertUser = String.format("insert into user (name, lastName, age) values ('%s', '%s', %d)", name, lastName, age);
            statement.executeUpdate(insertUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            String removeUserById = String.format("delete from user where id = %s", id);
            statement.executeUpdate(removeUserById);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {

            String getAllUsers = "select name, lastName, age from user";
            ResultSet resultSet = statement.executeQuery(getAllUsers);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                result.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String cleanUsersTable = "delete from user";
            statement.executeUpdate(cleanUsersTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

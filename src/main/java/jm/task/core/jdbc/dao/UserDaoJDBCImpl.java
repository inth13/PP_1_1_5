//Обработка всех исключений, связанных с работой с базой данных должна находиться в dao
//возраст должен быть в типе байт
//Statement, Connection - автоклозабл - используй трай с ресурсами

package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    /**
     * Connection вынесен из блока try что бы была возможность использовать Connection.rollback() в блоке catch
     */
    public void createUsersTable() {

        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            String sqlCreateTableUser = """
                    create table if not exists user
                    (
                        id       int auto_increment primary key,
                        name     varchar(255) null,
                        lastName varchar(255) null,
                        age      int(3)       null
                    );
                    """;
            connection.setAutoCommit(false);
            statement.executeUpdate(sqlCreateTableUser);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {

            String sqlDropTableUser = "drop table if exists user";

            connection.setAutoCommit(false);
            statement.executeUpdate(sqlDropTableUser);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        String sqlInsertUser = "insert into user (name, lastName, age) values (?,?,?)";

        try (PreparedStatement preparedStatementInsertUser = connection.prepareStatement(sqlInsertUser)) {
            preparedStatementInsertUser.setString(1, name);
            preparedStatementInsertUser.setString(2, lastName);
            preparedStatementInsertUser.setByte(3, age);

            connection.setAutoCommit(false);
            preparedStatementInsertUser.executeUpdate();
            connection.commit();
            System.out.printf("User с именем – %s добавлен в базу данных \n", name);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        String sqlRemoveUserById = "delete from user where id = ?";
        try (PreparedStatement deleteUserPrepareStatement = connection.prepareStatement(sqlRemoveUserById)) {
            connection.setAutoCommit(false);
            deleteUserPrepareStatement.setLong(1, id);
            deleteUserPrepareStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*
     * Здесь не вызывается rollback() поэтому Connection и Statement помещаю в try с ресурсами
     *
     *
     * */
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            String sqlGetAllUsers = "select name, lastName, age from user";
            ResultSet resultSet = statement.executeQuery(sqlGetAllUsers);

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
        Connection connection = Util.getConnection();
        try (Statement statement = connection.createStatement()) {
            String sqlCleanUserTable = "delete from user";
            connection.setAutoCommit(false);
            statement.executeUpdate(sqlCleanUserTable);
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

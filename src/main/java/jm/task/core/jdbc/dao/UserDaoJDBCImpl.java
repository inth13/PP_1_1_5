package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
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
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = Util.getConnection();
            statement = connection.createStatement();

            String sqlDropTableUser = "drop table if exists user";

            connection.setAutoCommit(false);
            statement.executeUpdate(sqlDropTableUser);
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sqlInsertUser = "insert into user (name, lastName, age) values (?,?,?)";
        Connection connection = null;
        PreparedStatement preparedStatementInsertUser = null;
        try {
            connection = Util.getConnection();
            preparedStatementInsertUser = connection.prepareStatement(sqlInsertUser);
            preparedStatementInsertUser.setString(1, name);
            preparedStatementInsertUser.setString(2, lastName);
            preparedStatementInsertUser.setByte(3, age);

            connection.setAutoCommit(false);
            preparedStatementInsertUser.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatementInsertUser != null) {
                preparedStatementInsertUser.close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sqlRemoveUserById = "delete from user where id = ?";
        Connection connection = null;
        PreparedStatement deleteUserPrepareStatement = null;
        try {
            connection = Util.getConnection();
            connection.setAutoCommit(false);
            deleteUserPrepareStatement = connection.prepareStatement(sqlRemoveUserById);
            deleteUserPrepareStatement.setLong(1, id);
            deleteUserPrepareStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteUserPrepareStatement != null) {
                deleteUserPrepareStatement.close();
            }
        }
    }

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

    public void cleanUsersTable() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = Util.getConnection();
            statement = connection.createStatement();
            String sqlCleanUserTable = "delete from user";
            connection.setAutoCommit(false);
            statement.executeUpdate(sqlCleanUserTable);
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
}

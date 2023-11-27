package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String DELETE_SQL = """
            delete from users where id = ?;
            """;
    private String SAVE_USER_SQL = """
            insert into users(name, last_name, age)
            values (?,?,?);
            """;
    private String CREATE_USERS_TABLE_SQL = """
            create table if not exists users(
            id bigint primary key auto_increment,
            name varchar(30),
            last_name varchar(30),
            age int
            );
            """;
    private String DROP_USERS_TABLE_SQL = """
            drop table if exists users;
            """;
    private String CLEAN_USERS_TABLE = """
            truncate table users;
            """;
    private String GET_ALL_USERS_SQL = """
            select id, name, last_name, age from users;
            """;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERS_TABLE_SQL)){
            preparedStatement.execute(CREATE_USERS_TABLE_SQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DROP_USERS_TABLE_SQL)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_SQL)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS_SQL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_USERS_TABLE)){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

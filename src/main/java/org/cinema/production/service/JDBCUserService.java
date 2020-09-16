package org.cinema.production.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.cinema.production.model.User;
import org.cinema.production.model.UserType;


public class JDBCUserService {

  public static User currentUser;
  public static final String RESET_PASSWORD = "StartPassword";
  public static List<User> allUsers = new ArrayList<>();

  public static List<User> getAllUsers() throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection,
        "SELECT * FROM users");
    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);

    int id = 0;
    String login = "";
    String password = "";
    UserType userType = null;

    List<User> users = new ArrayList<>();

    while (resultSet.next()) {
      id = resultSet.getInt("user_id");
      login = resultSet.getString("login");
      password = resultSet.getString("password");
      userType = UserType.valueOf(resultSet.getString("user_type").toUpperCase());
      users.add(new User(id, login, password, userType));
    }

    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);

    return users;
  }

  public static User addUser(User user) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, ""
        + "INSERT INTO users(login, password, user_type) VALUES (?, ?, ?);");

    preparedStatement.setString(1, user.getLogin());
    preparedStatement.setString(2, user.getPassword());
    preparedStatement.setString(3, user.getType().toString());
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
    return user;
  }

  public static void deleteUser(User user) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "DELETE FROM "
        + "users WHERE login = ?");
    preparedStatement.setString(1, user.getLogin());
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static void updateUser(User user) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "UPDATE users "
        + "SET login = ?, password = ? WHERE user_id = ?");
    preparedStatement.setString(1, user.getLogin());
    preparedStatement.setString(2, user.getPassword());
    preparedStatement.setInt(3, user.getId());
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }


  public static boolean isSuchUser(User user) throws SQLException {
    allUsers = getAllUsers();
    if (allUsers.contains(user)) {
      return true;
    } else {
      return false;
    }
  }

  public static void showAllSimpleUsers() {
    for (int i = 0; i < allUsers.size(); i++) {
      User user = allUsers.get(i);
      if (user.getType().equals(UserType.USER)) {
        System.out.println(user);
      }
    }
  }

}

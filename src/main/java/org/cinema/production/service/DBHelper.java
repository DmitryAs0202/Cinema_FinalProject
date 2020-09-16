package org.cinema.production.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {

  static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
  static final String DATABASE_URL = "jdbc:mysql://localhost:3306/cinemadb?useSSL=false";
  static final String USER = "root";
  static final String PASSWORD = "StrongPassword3332";

  private static DBHelper instance;

  Connection connection;
  PreparedStatement statement;
  ResultSet resultSet;

  public static DBHelper getInstance() {

    if (instance == null) {
      instance = new DBHelper();
    }
    return instance;
  }

  public Connection openConnection() throws SQLException {

    try {
      Class.forName(JDBC_DRIVER);
      connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    return connection;
  }

  public void closeConnection(Connection connection) {

    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public PreparedStatement openStatement(Connection connection, String query) {

    try {
      statement = connection.prepareStatement(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return statement;
  }

  public void closeStatement(PreparedStatement preparedStatement) {

    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public ResultSet openResultSet(PreparedStatement preparedStatement) {

    try {
      resultSet = statement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return resultSet;
  }

  public void closeResultSet(ResultSet resultSet) {

    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

}

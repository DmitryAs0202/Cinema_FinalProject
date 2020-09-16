package org.cinema.production.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.cinema.production.model.Film;
import org.cinema.production.model.Ticket;

public class JDBCFilmService {

  public static Map<Integer, Film> films;
  private static final String DATE_REGEXP = "[.:\\s]";


  public static Map<Integer, Film> getAllFilms() throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection,
        "SELECT * FROM films");
    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);
    int id = 0;
    String name = "";
    LocalDateTime date = null;
    Map<Integer, Film> availableFilms = new TreeMap<>();

    while (resultSet.next()) {
      id = resultSet.getInt("film_id");
      name = resultSet.getString("name");
      String stringDate = resultSet.getString("date");
      String[] dateValues = stringDate.split(DATE_REGEXP);
      date = LocalDateTime.of(Integer.parseInt(dateValues[2]), Integer.parseInt(dateValues[1]),
          Integer.parseInt(dateValues[0]), Integer.parseInt(dateValues[3]),
          Integer.parseInt(dateValues[4]));
      availableFilms.put(id, new Film(id, name, date));
    }
    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);

    return availableFilms;
  }

  public static void getFilmsWithTickets() throws SQLException {
    films = getAllFilms();
    JDBCTicketService.tickets = JDBCTicketService.getAllAvailableTickets();
    for (int i = 0; i < JDBCTicketService.tickets.size(); i++) {
      Ticket ticket = JDBCTicketService.tickets.get(i);
      int filmID = ticket.getFilm().getId();
      Film film = films.get(filmID);
      film.addTicket(ticket);
      films.put(filmID, film);
    }
  }

  public static void updateMovieTitle(Film film, String name) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "UPDATE films "
        + "SET name = ? WHERE film_id = ?");
    preparedStatement.setString(1, name);
    preparedStatement.setInt(2, film.getId());
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static void updateMovieDate(Film film, String date) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "UPDATE films "
        + "SET date = ? WHERE film_id = ?");
    preparedStatement.setString(1, date);
    preparedStatement.setInt(2, film.getId());
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static void addNewMovie(String name, String date) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "INSERT INTO "
        + "films(name, date) VALUES (?, ?)");
    preparedStatement.setString(1, name);
    preparedStatement.setString(2, date);
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static void deleteMovie(int filmID) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "DELETE FROM "
        + "films WHERE film_id = ?");
    preparedStatement.setInt(1, filmID);
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static int getLastFilmID() throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "SELECT "
        + "MAX(film_id)  FROM films");
    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);
    int filmID = 0;
    if (resultSet.next()) {
      filmID = resultSet.getInt(1);
    }
    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
    return filmID;
  }

  public static void showAllFilms() {
    films.forEach((key, value) -> System.out.println(key + ". " + value));
  }

}

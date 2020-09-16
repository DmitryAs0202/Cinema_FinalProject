package org.cinema.production.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.cinema.production.model.Film;
import org.cinema.production.model.Ticket;
import org.cinema.production.model.User;

public class JDBCTicketService {

  public static List<Ticket> tickets;
  public static Map<Integer, Ticket> ticketMap;
  public static final String REGEX_FOR_CHECK = " ";
  public static List<Ticket> userPurchased;
  public static Ticket currentTicket;
  private static final int STANDARD_PRICE = 10;
  private static final int VIP_PRICE = 25;

  public static List<Ticket> getAllAvailableTickets() throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection,
        "SELECT * FROM available_tickets");
    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);
    int id = 0;
    User user = null;
    Film film = null;
    int seatNumber = 0;
    int price = 0;
    boolean isAvailable = true;
    JDBCFilmService.films = JDBCFilmService.getAllFilms();
    List<Ticket> ticketList = new ArrayList<>();

    while (resultSet.next()) {
      id = resultSet.getInt("ticket_id");
      int filmID = resultSet.getInt("film_id");
      film = JDBCFilmService.films.get(filmID);
      seatNumber = resultSet.getInt("seat_number");
      price = resultSet.getInt("price");
      String available = resultSet.getString("is_available");
      if (available.equals("false")) {
        isAvailable = false;
      }
      ticketList.add(new Ticket(id, user, film, seatNumber, price, isAvailable));
    }
    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);

    return ticketList;
  }

  public static void showTicketsForFilm(int filmID) throws SQLException {
    JDBCFilmService.getFilmsWithTickets();
    Film film = JDBCFilmService.films.get(filmID);
    List<Ticket> ticketList = film.getTickets();
    tickets = ticketList;
    ticketList.forEach(ticket -> System.out.println(ticket));
  }

  public static void addTicketToAvailable(Ticket ticket) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, ""
        + "INSERT INTO available_tickets(ticket_id, owner_name, film_id, seat_number, price, "
        + "is_available) VALUES (?, ?, ?, ?, ?, ?);");
    preparedStatement.setInt(1, ticket.getId());
    preparedStatement.setString(2, "null");
    preparedStatement.setInt(3, ticket.getFilm().getId());
    preparedStatement.setInt(4, ticket.getSeatNumber());
    preparedStatement.setInt(5, ticket.getPrice());
    preparedStatement.setString(6, "true");
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static void addTicketToPurchased(Ticket ticket) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, ""
        + "INSERT INTO purchased_tickets(id, owner_name, film_id, seat_number, price) "
        + "VALUES (?, ?, ?, ?, ?);");
    preparedStatement.setInt(1, ticket.getId());
    preparedStatement.setString(2, ticket.getUser().getLogin());
    preparedStatement.setInt(3, ticket.getFilm().getId());
    preparedStatement.setInt(4, ticket.getSeatNumber());
    preparedStatement.setInt(5, ticket.getPrice());
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static void addTicketToPurchased(Ticket ticket, String owner) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, ""
        + "INSERT INTO purchased_tickets(id, owner_name, film_id, seat_number, price) "
        + "VALUES (?, ?, ?, ?, ?);");
    preparedStatement.setInt(1, ticket.getId());
    preparedStatement.setString(2, owner);
    preparedStatement.setInt(3, ticket.getFilm().getId());
    preparedStatement.setInt(4, ticket.getSeatNumber());
    preparedStatement.setInt(5, ticket.getPrice());
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static void removeTicket(Ticket ticket, int type) throws SQLException {
    int id = ticket.getId();
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement;
    switch (type) {
      case 1:
        preparedStatement = dbHelper.openStatement(connection,
            "DELETE  FROM available_tickets WHERE ticket_id = ?");
        break;
      case 2:
        preparedStatement = dbHelper.openStatement(connection,
            "DELETE  FROM purchased_tickets WHERE id = ?");
        break;
      case 3:
        preparedStatement = dbHelper.openStatement(connection,
            "DELETE  FROM booked_tickets WHERE id = ?");
        break;
      default:
        preparedStatement = dbHelper.openStatement(connection,
            "DELETE  FROM available_tickets WHERE id = ?");
        break;
    }
    preparedStatement.setInt(1, id);
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static List<Ticket> purchase(String value) {
    ticketMap = new TreeMap<>();
    List<Ticket> ticketList = new ArrayList<>();
    for (int i = 0; i < tickets.size(); i++) {
      ticketMap.put(tickets.get(i).getId(), tickets.get(i));
    }

    String[] values = value.split(REGEX_FOR_CHECK);

    for (int i = 0; i < values.length; i++) {
      int key = Integer.parseInt(values[i]);
      if (ticketMap.containsKey(key)) {
        ticketList.add(ticketMap.get(key));
      }
    }
    return ticketList;
  }

  public static void multipleTicketsRemove(List<Ticket> ticketList, int type) throws SQLException {
    for (int i = 0; i < ticketList.size(); i++) {
      Ticket ticket = ticketList.get(i);
      removeTicket(ticket, type);
    }

  }

  public static void moveTicketsToPurchased(List<Ticket> ticketList) throws SQLException {
    for (int i = 0; i < ticketList.size(); i++) {
      Ticket ticket = ticketList.get(i);
      ticket.setUser(JDBCUserService.currentUser);
      addTicketToPurchased(ticket);
    }

  }

  public static Map<Integer, Ticket> getTickets(int type) throws SQLException {
    String ownerName = JDBCUserService.currentUser.getLogin();
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement;
    if (type == 1) {
      preparedStatement = dbHelper.openStatement(connection,
          "SELECT * FROM purchased_tickets WHERE owner_name = ?");
    } else {
      preparedStatement = dbHelper.openStatement(connection,
          "SELECT * FROM booked_tickets WHERE owner_name = ?");
    }
    preparedStatement.setString(1, ownerName);
    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);
    int id = 0;
    int filmId = 0;
    int seatNumber = 0;
    int price = 0;

    Map<Integer, Ticket> ticketMap = new TreeMap<>();
    JDBCFilmService.films = JDBCFilmService.getAllFilms();

    while (resultSet.next()) {
      id = resultSet.getInt("id");
      filmId = resultSet.getInt("film_id");
      seatNumber = resultSet.getInt("seat_number");
      price = resultSet.getInt("price");
      ticketMap
          .put(id, new Ticket(id, JDBCUserService.currentUser, JDBCFilmService.films.get(filmId),
              seatNumber, price, false));
    }
    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
    return ticketMap;
  }

  public static Map<Integer, Ticket> getTickets(String ownerName) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection,
        "SELECT * FROM purchased_tickets WHERE owner_name = ?");
    preparedStatement.setString(1, ownerName);

    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);
    int id = 0;
    int filmId = 0;
    int seatNumber = 0;
    int price = 0;

    Map<Integer, Ticket> ticketMap = new TreeMap<>();
    JDBCFilmService.films = JDBCFilmService.getAllFilms();

    while (resultSet.next()) {
      id = resultSet.getInt("id");
      filmId = resultSet.getInt("film_id");
      seatNumber = resultSet.getInt("seat_number");
      price = resultSet.getInt("price");
      ticketMap
          .put(id, new Ticket(id, new User(ownerName), JDBCFilmService.films.get(filmId),
              seatNumber, price, false));
    }
    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
    return ticketMap;
  }

  public static void addTicketToBooked(Ticket ticket, String owner) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, ""
        + "INSERT INTO booked_tickets(id, owner_name, film_id, seat_number, price) "
        + "VALUES (?, ?, ?, ?, ?);");
    preparedStatement.setInt(1, ticket.getId());
    preparedStatement.setString(2, owner);
    preparedStatement.setInt(3, ticket.getFilm().getId());
    preparedStatement.setInt(4, ticket.getSeatNumber());
    preparedStatement.setInt(5, ticket.getPrice());
    preparedStatement.execute();

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);

  }

  public static void addNewMovieTickets(int standardAmount, int vipAmount) throws SQLException {
    int filmID = JDBCFilmService.getLastFilmID();
    int seatCounter = 0;
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "INSERT INTO "
        + "available_tickets(film_id, seat_number, price) VALUES (?, ?, ?)");
    for (int i = 0; i < standardAmount; i++) {
      preparedStatement.setInt(1, filmID);
      preparedStatement.setInt(2, ++seatCounter);
      preparedStatement.setInt(3, STANDARD_PRICE);
      preparedStatement.execute();
    }

    for (int i = 0; i < vipAmount; i++) {
      preparedStatement.setInt(1, filmID);
      preparedStatement.setInt(2, ++seatCounter);
      preparedStatement.setInt(3, VIP_PRICE);
      preparedStatement.execute();
    }

    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
  }

  public static void addAdditionalTickets(int standardAmount, int vipAmount, int filmID)
      throws SQLException {
    List<Integer> seats = new ArrayList<>();
    seats.add(getMaxSeat(filmID));
    seats.add(getMaxPurchasedSeat(filmID));
    seats.add(getMaxBookedSeat(filmID));
    int seatCounter = Collections.max(seats);
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "INSERT INTO "
        + "available_tickets(film_id, seat_number, price) VALUES (?, ?, ?)");
    for (int i = 0; i < standardAmount; i++) {
      preparedStatement.setInt(1, filmID);
      preparedStatement.setInt(2, ++seatCounter);
      preparedStatement.setInt(3, STANDARD_PRICE);
      preparedStatement.execute();
    }

    for (int i = 0; i < vipAmount; i++) {
      preparedStatement.setInt(1, filmID);
      preparedStatement.setInt(2, ++seatCounter);
      preparedStatement.setInt(3, VIP_PRICE);
      preparedStatement.execute();
    }
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);

  }

  public static int getMaxSeat(int filmID) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "SELECT "
        + "MAX(seat_number)  FROM available_tickets WHERE film_id = ?");
    preparedStatement.setInt(1, filmID);
    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);
    int maxSeat = 0;
    if (resultSet.next()) {
      maxSeat = resultSet.getInt(1);
    }
    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
    return maxSeat;
  }

  public static int getMaxPurchasedSeat(int filmID) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "SELECT "
        + "MAX(seat_number)  FROM purchased_tickets WHERE film_id = ?");
    preparedStatement.setInt(1, filmID);
    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);
    int maxSeat = 0;
    if (resultSet.next()) {
      maxSeat = resultSet.getInt(1);
    }
    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
    return maxSeat;
  }

  public static int getMaxBookedSeat(int filmID) throws SQLException {
    DBHelper dbHelper = DBHelper.getInstance();
    Connection connection = dbHelper.openConnection();
    PreparedStatement preparedStatement = dbHelper.openStatement(connection, "SELECT "
        + "MAX(seat_number)  FROM booked_tickets WHERE film_id = ?");
    preparedStatement.setInt(1, filmID);
    ResultSet resultSet = dbHelper.openResultSet(preparedStatement);
    int maxSeat = 0;
    if (resultSet.next()) {
      maxSeat = resultSet.getInt(1);
    }
    dbHelper.closeResultSet(resultSet);
    dbHelper.closeStatement(preparedStatement);
    dbHelper.closeConnection(connection);
    return maxSeat;
  }

  public static boolean isSuchTicket(int ticketID) {
    for (int i = 0; i < tickets.size(); i++) {
      Ticket ticket = tickets.get(i);
      if (ticket.getId() == ticketID) {
        currentTicket = ticket;
      }
    }
    if (currentTicket != null) {
      return true;
    } else {
      return false;
    }
  }

  public static void showPurchasedTicketsFromList() {
    userPurchased.forEach(ticket -> System.out.println(ticket));

  }

  public static void showTicketMap() {
    ticketMap.forEach((key, value) -> System.out.println(value));
  }

  public static void clearCurrentTickets() {
    userPurchased.clear();
  }

}

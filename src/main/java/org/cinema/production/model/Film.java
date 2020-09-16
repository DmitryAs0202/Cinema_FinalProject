package org.cinema.production.model;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Film {

  public static final String FORMAT = "dd.MM.yyyy HH:mm";

  private int id;
  private String name;
  private LocalDateTime date;
  private List<Ticket> tickets;

  public Film(int id, String name, LocalDateTime date,
      List<Ticket> tickets) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.tickets = tickets;
  }

  public Film(int id, String name, LocalDateTime date) {
    this.id = id;
    this.name = name;
    this.date = date;
    tickets = new ArrayList<>();
  }

  public Film(String name) {
    this.name = name;
  }

  public void addTicket(Ticket ticket) {
    tickets.add(ticket);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDate() {
    return date.format((DateTimeFormatter.ofPattern(FORMAT)));
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public List<Ticket> getTickets() {
    return tickets;
  }

  public void setTickets(List<Ticket> tickets) {
    this.tickets = tickets;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Film)) {
      return false;
    }
    Film film = (Film) o;
    return Objects.equals(getName(), film.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Film: ");
    sb.append(name).append(", ");
    sb.append("Дата/Date ").append(getDate()).append(".");
    return sb.toString();
  }
}

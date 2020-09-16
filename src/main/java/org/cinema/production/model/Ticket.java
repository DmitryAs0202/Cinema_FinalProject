package org.cinema.production.model;

import java.util.Objects;

public class Ticket {

  private int id;
  private User user;
  private Film film;
  private int seatNumber;
  private int price;
  private boolean isAvailable;

  public Ticket(int id, User user, Film film, int seatNumber, int price, boolean isAvailable) {
    this.id = id;
    this.user = user;
    this.film = film;
    this.seatNumber = seatNumber;
    this.price = price;
    this.isAvailable = isAvailable;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Film getFilm() {
    return film;
  }

  public void setFilm(Film film) {
    this.film = film;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Ticket)) {
      return false;
    }
    Ticket ticket = (Ticket) o;
    return getSeatNumber() == ticket.getSeatNumber() &&
        Objects.equals(getFilm(), ticket.getFilm());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFilm(), getSeatNumber());
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Билет/Ticket № ");
    sb.append(id).append(", ").append("Фильм/Film: ");
    sb.append(film.getName()).append(", Место/Seat number: ");
    sb.append(seatNumber).append(", Цена/Price: ");
    sb.append(price).append(" BYN ");
    sb.append("Дата/Date: ").append(film.getDate());
    return sb.toString();
  }
}

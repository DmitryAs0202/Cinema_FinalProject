package org.cinema.production.model;

import java.util.Objects;

public class User {

  private int id;
  private String login;
  private String password;
  private UserType type;

  public User(int id, String login, String password, UserType type) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.type = type;
  }

  public User(String login, String password, UserType type) {
    this.login = login;
    this.password = password;
    this.type = type;
  }

  public User(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public User(String login) {
    this.login = login;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserType getType() {
    return type;
  }

  public void setType(UserType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(getLogin(), user.getLogin());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getLogin());
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User(");
    sb.append("id= ").append(id).append(",");
    sb.append("Login: ").append(login).append(", ");
    sb.append("User type: ").append(type.getRussianName()).append(")");
    return sb.toString();
  }
}

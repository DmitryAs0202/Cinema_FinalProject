package org.cinema.production.model;

public enum UserType {

  ADMIN("Администратор"),
  MANAGER("Менеджер"),
  USER("Пользователь");

  private String russianName;

  UserType(String russianName) {
    this.russianName = russianName;
  }

  public String getRussianName() {
    return russianName;
  }

  public void setRussianName(String russianName) {
    this.russianName = russianName;
  }
}

package org.cinema.production.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.cinema.production.model.Film;
import org.cinema.production.model.Ticket;

public class LogService {

  private static final String LOGS_PATH = "src/main/java/org/cinema/production/logs/";
  private static final String FORMATTER = "dd.MM.yyyy HH:mm";
  private static String userName = "";

  public static void registration() {
    userName = JDBCUserService.currentUser.getLogin();
    File file = new File(LOGS_PATH + userName + ".log");
    LocalDateTime date = LocalDateTime.now();
    try {
      PrintWriter pw = new PrintWriter(file);
      pw.println(
          date.format(DateTimeFormatter.ofPattern(FORMATTER)) + " Пользователь " + userName + " "
              + "зарегистрирован.");
      pw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public static List<String> readLog() {
    userName = JDBCUserService.currentUser.getLogin();
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = new ArrayList<>();
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        log.add(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return log;
  }

  public static void writeLog(List<String> log, File file) {
    try {
      PrintWriter pw = new PrintWriter(file);
      log.forEach(line -> pw.println(line));
      pw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  public static void login() {
    userName = JDBCUserService.currentUser.getLogin();
    File file = new File(LOGS_PATH + userName + ".log");
    LocalDateTime date = LocalDateTime.now();
    List<String> log = readLog();
    log.add(date.format(DateTimeFormatter.ofPattern(FORMATTER)) + " " + userName
        + " вошел в приложение.");
    writeLog(log, file);
  }

  public static void logOut() {
    File file = new File(LOGS_PATH + userName + ".log");
    LocalDateTime date = LocalDateTime.now();
    List<String> log = readLog();
    log.add(date.format(DateTimeFormatter.ofPattern(FORMATTER)) + " " + userName
        + " вышел из учетной записи.");
    writeLog(log, file);
  }

  public static void showMovies() {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " посмотрел доступные фильмы.");
    writeLog(log, file);
  }

  public static void choosing() {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " открыл меню выбора билетов.");
    writeLog(log, file);
  }

  public static void purchase() {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " приобрел билеты: ");
    JDBCTicketService.userPurchased.forEach(ticket -> log.add(ticket.toString()));
    writeLog(log, file);
  }

  public static void returnTicket(Ticket ticket) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " вернул билет: ");
    log.add(ticket.toString());
    writeLog(log, file);
  }

  public static void reservation(String clientName) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " заброинровал для пользователя " + clientName + " билет:");
    log.add(JDBCTicketService.currentTicket.toString());
    writeLog(log, file);
  }

  public static void returnMenu() {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " вошел в меню возврата билетов.");
    writeLog(log, file);
  }

  public static void closeReturnMenu() {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " вышел из меню возврата билетов.");
    writeLog(log, file);
  }

  public static void cancelReservation(Ticket ticket) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " отменил бронь на билет:");
    log.add(ticket.toString());
    writeLog(log, file);
  }

  public static void showTickets() {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " открыл список своих билетов.");
    writeLog(log, file);
  }

  public static void acceptReservation(Ticket ticket) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " выкупил забронированный билет:");
    log.add(ticket.toString());
    writeLog(log, file);
  }

  public static void editionName(Film film) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " отредактировал название фильма " + film.toString());
    writeLog(log, file);
  }

  public static void editionDate(Film film, String date) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " изменил время показа фильма " + film.toString() + " Новая дата: " + date);
    writeLog(log, file);
  }

  public static void purchaseForClient(String clientName, Ticket ticket) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " приобрел по запросу " + clientName + " билет:");
    log.add(ticket.toString());
    writeLog(log, file);
  }

  public static void returnForClient(String clientName, Ticket ticket) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " вернул по запросу " + clientName + " билет:");
    log.add(ticket.toString());
    writeLog(log, file);
  }

  public static void addNewUser(String user) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " добавил нового пользователя: " + user);
    writeLog(log, file);
  }

  public static void deleteUser(String user) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " удалил учетную запись пользователя " + user);
    writeLog(log, file);
  }

  public static void editUser(String oldName, String newName) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " изменил логин пользователя " + oldName + ". Новый логин- " + newName);
    writeLog(log, file);
  }

  public static void openEditionMenu() {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " вошел в меню редактирования пользователей.");
    writeLog(log, file);
  }

  public static void closeEditionMenu() {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " вышел из меню редактирования пользователей.");
    writeLog(log, file);
  }

  public static void dropPassword(String user) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(
        userName + " сбросил пароль пользователя " + user + ". Установлен пароль по умолчанию.");
    writeLog(log, file);
  }

  public static void addNewMovie(String film, int standard, int vip) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " добавил новый фильм " + film);
    log.add("Добавлено стандартных билетов: " + standard);
    log.add("Добавлено vip билетов: " + vip);
    writeLog(log, file);
  }

  public static void addAdditionalTickets(Film film, int standard, int vip) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " добавил билеты к фильму " + film);
    log.add("Добавлено стандартных билетов: " + standard);
    log.add("Добавлено vip билетов: " + vip);
    writeLog(log, file);
  }

  public static void removeFilm(Film film) {
    File file = new File(LOGS_PATH + userName + ".log");
    List<String> log = readLog();
    log.add(userName + " удалил фильм " + film + ". Фильм больше недоступен для пользователей.");
    writeLog(log, file);
  }


}

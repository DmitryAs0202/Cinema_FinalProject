package org.cinema.production.view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;
import org.cinema.production.model.Ticket;
import org.cinema.production.model.User;
import org.cinema.production.model.UserType;
import org.cinema.production.service.JDBCFilmService;
import org.cinema.production.service.JDBCTicketService;
import org.cinema.production.service.JDBCUserService;
import org.cinema.production.service.LogService;
import org.cinema.production.service.Security;
;

public class Main {

  public static String language;
  public static final String DEFAULT_LANGUAGE = "Russian";


  public static void main(String[] args) {

    startConsole();
    //System.out.println(Security.getHashPassword("StartPassword"));

  }

  public static void startConsole() {
    System.out.println("Приветствуем!/Welcome!");
    System.out.println("Пожалуйста, выберите язык меню/Please select the menu language.");
    System.out.println("1.Русский");
    System.out.println("2.English");
    System.out.print("Ваш выбор/Your choice: ");
    Scanner sc = new Scanner(System.in);
    int choice = sc.nextInt();
    switch (choice) {
      case 1:
        language = DEFAULT_LANGUAGE;
        startMenu();
        break;
      case 2:
        language = "English";
        startMenu();
        break;
      default:
        System.out.println("Выбран несуществующий пункт меню/Wrong menu item.");
        System.out.println();
        startConsole();
        break;
    }
  }

  public static void startMenu() {
    if (language.equals(DEFAULT_LANGUAGE)) {
      System.out.println("1. Регистрация.");
      System.out.println("2. Вход.");
      System.out.println("3. Выход из приложения");
      System.out.print("Выберите пункт из меню: ");
    } else {
      System.out.println("1. Create new account.");
      System.out.println("2. Login.");
      System.out.println("3. Close application.");
      System.out.print("Select menu item: ");
    }

    Scanner scanner = new Scanner(System.in);
    int key;
    key = scanner.nextInt();
    switch (key) {
      case 1:
        accountCreation();
        break;
      case 2:
        logIn();
        break;
      case 3:
        System.exit(0);
      default:
        if (language.equals(DEFAULT_LANGUAGE)) {
          System.out.println("Вы ввели неверное значение меню...\n");
        } else {
          System.out.println("Wrong menu item...\n");
        }
        startMenu();
        break;
    }
  }

  public static void accountCreation() {
    Scanner scanner = new Scanner(System.in);
    if (language.equals(DEFAULT_LANGUAGE)) {
      System.out.print("Введите логин: ");
    } else {
      System.out.print("Enter username: ");
    }
    String login = scanner.nextLine();
    if (language.equals(DEFAULT_LANGUAGE)) {
      System.out.print("Введите пароль: ");
    } else {
      System.out.print("Enter password: ");
    }
    String password = Security.getHashPassword(scanner.nextLine());
    User user = new User(login, password, UserType.USER);

    try {
      if (!JDBCUserService.isSuchUser(user)) {
        JDBCUserService.currentUser = JDBCUserService.addUser(user);
        LogService.registration();
        if (language.equals(DEFAULT_LANGUAGE)) {
          System.out.println("Успешная регистрация!");
        } else {
          System.out.println("The account was created successfully!");
        }
        showActions();
      } else {
        if (language.equals(DEFAULT_LANGUAGE)) {
          System.out.println("Такой пользователь уже существует.");
        } else {
          System.out.println("Such user already exists.");
        }
        System.out.println();
        startMenu();
      }
    } catch (SQLException throwable) {
      if (language.equals(DEFAULT_LANGUAGE)) {
        System.out.println("Регистрация в данный момент недоступна, попробуйте позже...");
      } else {
        System.out.println("Registration is not possible at this time, please try again later...");
      }
      startMenu();
    }
  }

  public static void logIn() {
    Scanner scanner = new Scanner(System.in);
    switch (language) {
      case DEFAULT_LANGUAGE:
        System.out.print("Введите ваш логин: ");
        break;
      default:
        System.out.print("Enter username: ");
        break;
    }
    String login = scanner.nextLine();
    switch (language) {
      case "Russian":
        System.out.print("Введите пароль: ");
        break;
      default:
        System.out.print("Enter password: ");
        break;
    }
    String password = Security.getHashPassword(scanner.nextLine());
    User user = new User(login, password);
    try {
      if (JDBCUserService.isSuchUser(user)) {
        JDBCUserService.currentUser =
            JDBCUserService.allUsers.get(JDBCUserService.allUsers.indexOf(user));
        if (user.getPassword().equals(JDBCUserService.currentUser.getPassword())) {
          LogService.login();
          System.out.println();
          switch (JDBCUserService.currentUser.getType()) {
            case USER:
              showActions();
              break;
            case MANAGER:
              showManagerActions();
              break;
            case ADMIN:
              showAdminActions();
              break;
          }
        } else {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("Введены некорректные данные. Проверьте логин и пароль.");
              break;
            default:
              System.out.println("Invalid data was entered. Check your username and password");
              break;
          }
          startMenu();
        }

      } else {
        switch (language) {
          case DEFAULT_LANGUAGE:
            System.out.println("Введены некорректные данные. "
                + "Пользователя с таким именем не существет...");
            break;
          default:
            System.out.println("Invalid data was entered. There is no such user");
            break;
        }
        startMenu();
      }
    } catch (SQLException throwables) {
      switch (language) {
        case DEFAULT_LANGUAGE:
          System.out.println("Не удалось войти в учетную запись, попробуйте позже...");
          break;
        default:
          System.out.println("Unable to log in to your account, try again later...");
          break;
      }
      startMenu();
    }
  }

  public static void showActions() {
    switch (language) {
      case DEFAULT_LANGUAGE:
        System.out.println("1. Показать доступные фильмы.");
        System.out.println("2. Купить билет.");
        System.out.println("3. Вернуть билет, отменить бронь.");
        System.out.println("4. Показать мои билеты.");
        System.out.println("5. Выкупить забронированный билет.");
        System.out.println("6. Выйти из учетной записи.");
        System.out.print("Выберите пункт из меню: ");
        break;
      default:
        System.out.println("1. Show available movies.");
        System.out.println("2. Buy tickets.");
        System.out.println("3. Return tickets. Cancel ticket reservation.");
        System.out.println("4. Show my tickets.");
        System.out.println("5. Buy a reserved ticket.");
        System.out.println("6. Log out.");
        System.out.print("Select menu item: ");
        break;
    }
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();
    switch (choice) {
      case 1:
        try {
          JDBCFilmService.films = JDBCFilmService.getAllFilms();
          JDBCFilmService.showAllFilms();
          System.out.println();
          LogService.showMovies();
          showActions();
        } catch (SQLException throwables) {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("Ошибка! Данные обновляются, попробуйте позже.");
              break;
            default:
              System.out.println("Error. Please try again later.");
          }
          showActions();
        }
        break;
      case 2:
        choosingATicket();
        break;
      case 3:
        returnTicket();
        break;
      case 4:
        try {
          JDBCTicketService.ticketMap = JDBCTicketService.getTickets(1);
          if (JDBCTicketService.ticketMap.isEmpty()) {
            throw new Exception();
          }
          JDBCTicketService.showTicketMap();
          LogService.showTickets();
        } catch (Exception throwables) {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("У вас нет приобритенных билетов.");
              break;
            default:
              System.out.println("You have no purchased ticket.");
          }
          System.out.println();
          showActions();
        }
        showActions();
        break;
      case 5:
        try {
          JDBCTicketService.ticketMap = JDBCTicketService.getTickets(2);
          if (JDBCTicketService.ticketMap.isEmpty()) {
            throw new Exception();
          }
          JDBCTicketService.showTicketMap();
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.print("Введите номер билета, который хотите выкупить: ");
              break;
            default:
              System.out.print("Enter the ticket number you want to redeem: ");
              break;
          }
          int choice1 = scanner.nextInt();
          if (JDBCTicketService.ticketMap.containsKey(choice1)) {
            Ticket ticket = JDBCTicketService.ticketMap.get(choice1);
            JDBCTicketService.addTicketToPurchased(ticket);
            JDBCTicketService.removeTicket(ticket, 3);
            LogService.acceptReservation(ticket);
            switch (language) {
              case DEFAULT_LANGUAGE:
                System.out.println("Был выкуплен: " + ticket);
                break;
              default:
                System.out.println(ticket + " was redeemed.");
                break;
            }
            JDBCTicketService.ticketMap.clear();
            System.out.println();
            showActions();
          } else {
            switch (language) {
              case DEFAULT_LANGUAGE:
                System.out.println("Нет такого билета...");
                break;
              default:
                System.out.println("No such ticket...");
            }
            showActions();
          }
        } catch (SQLException e) {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("Соединение не установлено, попробуйте позже...");
              break;
            default:
              System.out.println("Connection error. Please try later...");
              break;
          }
          showActions();
        } catch (Exception throwables) {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("У вас нет забронированных билетов");
              break;
            default:
              System.out.println("You have no tickets booked");
              break;
          }
          showActions();
        }
        break;
      default:
        LogService.logOut();
        System.out.println();
        startMenu();
        break;
    }
  }

  public static void choosingATicket() {
    System.out.println();
    try {
      JDBCFilmService.films = JDBCFilmService.getAllFilms();
      JDBCFilmService.showAllFilms();
      LogService.choosing();
      switch (language) {
        case DEFAULT_LANGUAGE:
          System.out.print("Выберите номер фильма: ");
          break;
        default:
          System.out.print("Select movie number: ");
          break;
      }
      Scanner scanner = new Scanner(System.in);
      int choice = scanner.nextInt();
      if (JDBCFilmService.films.containsKey(choice)) {
        JDBCTicketService.showTicketsForFilm(choice);
        switch (language) {
          case DEFAULT_LANGUAGE:
            System.out.print("Введите номера билетов для покупки(№) через пробел: ");
            break;
          default:
            System.out.print("Enter the ticket`s numbers to purchase(№)  separated  by spaces: ");
        }
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String choice2 = bf.readLine();
        JDBCTicketService.userPurchased = JDBCTicketService.purchase(choice2);
        if (!JDBCTicketService.userPurchased.isEmpty()) {
          JDBCTicketService.moveTicketsToPurchased(JDBCTicketService.userPurchased);
          JDBCTicketService.multipleTicketsRemove(JDBCTicketService.userPurchased, 1);
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("Билеты приобритены! "
                  + "Если какого-либо билета не окажется в списке, возможно, "
                  + "вы не правильно указали номер...");
              break;
            default:
              System.out.println("Tickets purchased! If a ticket is not included in the list, "
                  + "you may have entered the wrong number...");
              break;
          }
          LogService.purchase();
          JDBCTicketService.showPurchasedTicketsFromList();
          JDBCTicketService.clearCurrentTickets();
        } else {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("Введены некорректные номера..");
              break;
            default:
              System.out.println("Invalid numbers were entered..");
              break;
          }
        }
        System.out.println();
        showActions();
      } else {
        switch (language) {
          case DEFAULT_LANGUAGE:
            System.out.println("Выбран несуществующий фильм...");
            break;
          default:
            System.out.println("Movie is not exist...");
        }
        choosingATicket();
      }
    } catch (SQLException throwables) {
      switch (language) {
        case DEFAULT_LANGUAGE:
          System.out.println("Ошибка соединения, попробуйте позже...");
          break;
        default:
          System.out.println("Connection failed, try again later...");
          break;
      }
      showActions();
    } catch (IOException e) {
      switch (language) {
        case DEFAULT_LANGUAGE:
          System.out.println("Ошибка ввода");
          break;
        default:
          System.out.println("Input error");
          break;
      }
      showActions();
    }
  }

  public static void returnTicket() {
    LogService.returnMenu();
    System.out.println();
    switch (language) {
      case DEFAULT_LANGUAGE:
        System.out.println("1. Вернуть купленный билет.");
        System.out.println("2. Отменить бронь.");
        System.out.println("3. Назад в меню.");
        System.out.print("Выберите пункт меню: ");
        break;
      default:
        System.out.println("1. Return ticket.");
        System.out.println("2. Cancel ticket reservation.");
        System.out.println("3. Back to menu.");
        System.out.print("Choose menu item: ");
    }
    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();
    switch (choice) {
      case 1:
        try {
          JDBCTicketService.ticketMap = JDBCTicketService.getTickets(1);
          if (JDBCTicketService.ticketMap.isEmpty()) {
            throw new Exception();
          }
          JDBCTicketService.showTicketMap();
        } catch (Exception throwables) {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("У вас нет купленных билетов...");
              break;
            default:
              System.out.println("You don't have any purchased tickets...");
              break;
          }
          showActions();
        }
        switch (language) {
          case DEFAULT_LANGUAGE:
            System.out.print("Введите номер билета, который хотели бы вернуть: ");
            break;
          default:
            System.out.print("Enter the ticket number you would like to return: ");
            break;
        }
        int number = scanner.nextInt();
        if (JDBCTicketService.ticketMap.containsKey(number)) {
          try {
            Ticket ticket = JDBCTicketService.ticketMap.get(number);
            JDBCTicketService.addTicketToAvailable(ticket);
            JDBCTicketService.removeTicket(ticket, 2);
            LogService.returnTicket(ticket);
            switch (language) {
              case DEFAULT_LANGUAGE:
                System.out.println(ticket + " был вернут успешно!");
                break;
              default:
                System.out.println(ticket + " was returned successfully!");
            }
            JDBCTicketService.ticketMap.clear();
            System.out.println();
            showActions();
          } catch (Exception throwables) {
            switch (language) {
              case DEFAULT_LANGUAGE:
                System.out.println("Не удалось веернуть билет. Попробуйте позже...");
                break;
              default:
                System.out.println("Failed to return ticket. try later");
                break;
            }
            showActions();
          }

        } else {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("Нет такого билета...");
              break;
            default:
              System.out.println("No such ticket...");
          }
          showActions();
        }
        break;
      case 2:
        try {
          JDBCTicketService.ticketMap = JDBCTicketService.getTickets(2);
          if (JDBCTicketService.ticketMap.isEmpty()) {
            throw new Exception();
          }
          JDBCTicketService.showTicketMap();
        } catch (Exception throwables) {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("У вас нет забронированных билетов");
              break;
            default:
              System.out.println("You have no tickets booked");
              break;
          }
          showActions();
        }
        switch (language) {
          case DEFAULT_LANGUAGE:
            System.out.print("Введите номер билета, бронь на который хотите отменить: ");
            break;
          default:
            System.out.print("Enter the ticket number you would like to cancel reservation: ");
            break;
        }
        int choice2 = scanner.nextInt();
        if (JDBCTicketService.ticketMap.containsKey(choice2)) {
          try {
            Ticket ticket = JDBCTicketService.ticketMap.get(choice2);
            JDBCTicketService.addTicketToAvailable(ticket);
            JDBCTicketService.removeTicket(ticket, 3);
            LogService.cancelReservation(ticket);
            switch (language) {
              case DEFAULT_LANGUAGE:
                System.out.println("Бронь на " + ticket + " была отменена.");
                break;
              default:
                System.out.println("Reservation has been canceled: " + ticket);
            }
            JDBCTicketService.ticketMap.clear();
            System.out.println();
            showActions();
          } catch (Exception throwables) {
            switch (language) {
              case DEFAULT_LANGUAGE:
                System.out.println("Не удалось отменить бронь, попробуйте позже...");
                break;
              default:
                System.out.println("Unable to cancel booking, please try again later");
                break;
            }
            showActions();
          }
        } else {
          switch (language) {
            case DEFAULT_LANGUAGE:
              System.out.println("Нет такого билета...");
              break;
            default:
              System.out.println("No such ticket...");
          }
          showActions();
        }
        break;
      default:
        LogService.closeReturnMenu();
        System.out.println();
        showActions();
        break;
    }

  }

  public static void showManagerActions() {
    System.out.println("MANAGER MENU");
    System.out.println("1. Редактировать название фильма.");
    System.out.println("2. Изменить дату показа фильма.");
    System.out.println("3. Забронировать билет для клиента.");
    System.out.println("4. Приобрести билет для клиента.");
    System.out.println("5. Вернуть билет клиента.");
    System.out.println("6. Выйти из учетной записи.");
    System.out.print("Выберите пункт меню: ");

    Scanner scanner = new Scanner(System.in);
    int choice = scanner.nextInt();
    switch (choice) {
      case 1:
        changeFilmName();
        break;
      case 2:
        changeFilmDate();
        break;
      case 3:
        bookATicket();
        break;
      case 4:
        buyTicketForClient();
        break;
      case 5:
        returnTicketForClient();
        break;
      default:
        LogService.logOut();
        System.out.println();
        startMenu();
        break;
    }


  }

  public static void changeFilmName() {
    Scanner scanner = new Scanner(System.in);
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    try {
      JDBCFilmService.films = JDBCFilmService.getAllFilms();
      JDBCFilmService.showAllFilms();
      System.out.print("Введите номер фильма для редактирования или введите \"0\" для "
          + "возврата в меню: ");
      int choice = scanner.nextInt();
      if (choice == 0) {
        switch (JDBCUserService.currentUser.getType()) {
          case MANAGER:
            showManagerActions();
            break;
          case ADMIN:
            showAdminActions();
            break;
        }
        return;
      }
      if (JDBCFilmService.films.containsKey(choice)) {
        System.out.println();
        System.out.println("Выбран фильм для редактирования: " + JDBCFilmService.films.get(choice));
        System.out.print("Введите новое название фильма: ");
        String newName = bf.readLine();
        JDBCFilmService.updateMovieTitle(JDBCFilmService.films.get(choice), newName);
        LogService.editionName(JDBCFilmService.films.get(choice));
        System.out.println("Название фильма успешно отредактировано.");
        JDBCFilmService.films.clear();
      } else {
        System.out.println("Нет фильма с таким id...");
      }
      System.out.println();
      switch (JDBCUserService.currentUser.getType()) {
        case ADMIN:
          showAdminActions();
          break;
        case MANAGER:
          showManagerActions();
          break;
      }
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой....");
      switch (JDBCUserService.currentUser.getType()) {
        case ADMIN:
          showAdminActions();
          break;
        case MANAGER:
          showManagerActions();
          break;
      }
    } catch (IOException e) {
      System.out.println("Ошибка ввода...");
      switch (JDBCUserService.currentUser.getType()) {
        case ADMIN:
          showAdminActions();
          break;
        case MANAGER:
          showManagerActions();
          break;
      }
    }
  }

  public static void changeFilmDate() {
    Scanner scanner = new Scanner(System.in);
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    try {
      JDBCFilmService.films = JDBCFilmService.getAllFilms();
      JDBCFilmService.showAllFilms();
      System.out.print("Введите номер фильма для редактирования или введите \"0\" для "
          + "возврата в меню: ");
      int choice = scanner.nextInt();
      if (choice == 0) {
        switch (JDBCUserService.currentUser.getType()) {
          case ADMIN:
            showAdminActions();
            break;
          case MANAGER:
            showManagerActions();
            break;
        }
        return;
      }
      if (JDBCFilmService.films.containsKey(choice)) {
        System.out.println();
        System.out.println("Выбран фильм для редактирования: " + JDBCFilmService.films.get(choice));
        System.out.print("Введите новую дату для показа фильма(в виде 01.01.2001 00:00): ");
        String newDate = bf.readLine();
        JDBCFilmService.updateMovieDate(JDBCFilmService.films.get(choice), newDate);
        LogService.editionDate(JDBCFilmService.films.get(choice), newDate);
        System.out.println("Дата показа фильма была изменена.");
        JDBCFilmService.films.clear();
      } else {
        System.out.println("Нет такого фильма...");
      }
      System.out.println();
      switch (JDBCUserService.currentUser.getType()) {
        case ADMIN:
          showAdminActions();
          break;
        case MANAGER:
          showManagerActions();
          break;
      }
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой...");
      switch (JDBCUserService.currentUser.getType()) {
        case ADMIN:
          showAdminActions();
          break;
        case MANAGER:
          showManagerActions();
          break;
      }
    } catch (IOException e) {
      System.out.println("Ошибка ввода...");
      switch (JDBCUserService.currentUser.getType()) {
        case ADMIN:
          showAdminActions();
          break;
        case MANAGER:
          showManagerActions();
          break;
      }
    }
  }

  public static void bookATicket() {
    System.out.println();
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    String clientName = getUserName();
    if (clientName == null) {
      showManagerActions();
      return;
    }
    try {
      if (JDBCUserService.isSuchUser(new User(clientName))) {
        System.out.println();
        int filmId = getFilmID();
        if (JDBCFilmService.films.containsKey(filmId)) {
          System.out.println();
          JDBCTicketService.showTicketsForFilm(filmId);
          System.out.print("Введите номер билета для брони: ");
          int ticketNumber = Integer.parseInt(bf.readLine());
          if (JDBCTicketService.isSuchTicket(ticketNumber)) {
            JDBCTicketService.addTicketToBooked(JDBCTicketService.currentTicket, clientName);
            JDBCTicketService.removeTicket(JDBCTicketService.currentTicket, 1);
            System.out.println(JDBCTicketService.currentTicket + " забронирован для пользователя " +
                clientName);
            LogService.reservation(clientName);
            JDBCTicketService.currentTicket = null;
            System.out.println();
            showManagerActions();
          } else {
            System.out.println("Номер билета введен некорректно");
          }
          System.out.println();
          showManagerActions();
        } else {
          System.out.println("Нет фильма с таким ID.");
          showManagerActions();
        }
      } else {
        System.out.println("Имя пользователя введено некорректно.");
        showManagerActions();
      }
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      showManagerActions();
    } catch (IOException e) {
      System.out.println("Ошибка ввода.");
      showManagerActions();
    }
  }

  public static void buyTicketForClient() {
    System.out.println();
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    String clientName = getUserName();
    if (clientName == null) {
      showManagerActions();
      return;
    }
    try {
      if (JDBCUserService.isSuchUser(new User(clientName))) {
        int filmID = getFilmID();
        if (JDBCFilmService.films.containsKey(filmID)) {
          System.out.println();
          JDBCTicketService.showTicketsForFilm(filmID);
          System.out.print("Введите номер билета для покупки: ");
          int ticketNumber = Integer.parseInt(bf.readLine());
          if (JDBCTicketService.isSuchTicket(ticketNumber)) {
            JDBCTicketService.addTicketToPurchased(JDBCTicketService.currentTicket, clientName);
            JDBCTicketService.removeTicket(JDBCTicketService.currentTicket, 1);
            System.out.println(JDBCTicketService.currentTicket + " приобретен для пользователя " +
                clientName);
            LogService.purchaseForClient(clientName, JDBCTicketService.currentTicket);
            JDBCTicketService.currentTicket = null;
            System.out.println();
            showManagerActions();
          } else {
            System.out.println("Нет такого билета.");
            showManagerActions();
          }

        } else {
          System.out.println("Нет фильма с таким ID.");
          showManagerActions();
        }
      } else {
        System.out.println("Имя пользователя введено некорректно.");
        showManagerActions();
      }
    } catch (SQLException throwables) {
      System.out.println("Ошибка сединения с базой.");
      showManagerActions();
    } catch (IOException e) {
      System.out.println("Ошибка ввода.");
      showManagerActions();
    }


  }

  public static void returnTicketForClient() {
    System.out.println();
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    String clientName = getUserName();
    if (clientName == null) {
      showManagerActions();
      return;
    }
    try {
      if (JDBCUserService.isSuchUser(new User(clientName))) {
        System.out.println();
        JDBCTicketService.ticketMap = JDBCTicketService.getTickets(clientName);
        if (JDBCTicketService.ticketMap.isEmpty()) {
          System.out.println("У пользователя нет билетов.");
          showManagerActions();
          return;
        }
        JDBCTicketService.showTicketMap();
        System.out.print("Введите номер билета, который необходимо вернуть: ");
        int ticketNumber = Integer.parseInt(bf.readLine());
        if (JDBCTicketService.ticketMap.containsKey(ticketNumber)) {
          JDBCTicketService.addTicketToAvailable(JDBCTicketService.ticketMap.get(ticketNumber));
          JDBCTicketService.removeTicket(JDBCTicketService.ticketMap.get(ticketNumber), 2);
          System.out.println(JDBCTicketService.ticketMap.get(ticketNumber) + " возвращен успешно.");
          LogService.returnForClient(clientName, JDBCTicketService.ticketMap.get(ticketNumber));
          JDBCTicketService.ticketMap.clear();
          System.out.println();
          showManagerActions();
        } else {
          System.out.println("Нет такого билета.");
          showManagerActions();
        }
      } else {
        System.out.println("Нет такого пользователя.");
        showManagerActions();
      }
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      showManagerActions();
    } catch (IOException e) {
      System.out.println("Ошибка ввода.");
      showManagerActions();
    }

  }

  public static void showAdminActions() {
    System.out.println("ADMINISTRATOR MENU");
    System.out.println("1. Добавить нового пользователя.");
    System.out.println("2. Удалить пользователя.");
    System.out.println("3. Редактировать пользователя.");
    System.out.println("4. Добавить доступный к просмотру фильм.");
    System.out.println("5. Добавить дополнительные билеты к фильму.");
    System.out.println("6. Удалить фильм из доступных к просмотру.");
    System.out.println("7. Редактировать название фильма.");
    System.out.println("8. Изменить дату показа фильма.");
    System.out.println("9. Выйти из учетной записи.");
    System.out.print("Выберите пункт меню: ");

    Scanner scanner = new Scanner(System.in);
    int item = scanner.nextInt();

    switch (item) {
      case 1:
        addNewUser();
        break;
      case 2:
        removeUser();
        break;
      case 3:
        updateUser();
        break;
      case 4:
        addNewFilm();
        break;
      case 5:
        addAdditionalTickets();
        break;
      case 6:
        removeFilm();
        break;
      case 7:
        changeFilmName();
        break;
      case 8:
        changeFilmDate();
        break;
      default:
        LogService.logOut();
        System.out.println();
        startMenu();
        break;
    }
  }

  public static String getUserName() {
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    String clientName = "";
    try {
      JDBCUserService.allUsers = JDBCUserService.getAllUsers();
      JDBCUserService.showAllSimpleUsers();
      System.out.print("Введите логин клиента для действия "
          + "или введите \"0\", чтобы вернуться в меню: ");
      clientName = bf.readLine();
      if (clientName.equals("0")) {
        return null;
      }
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      switch (JDBCUserService.currentUser.getType()) {
        case MANAGER:
          showManagerActions();
          break;
        case ADMIN:
          showAdminActions();
          break;
      }
    } catch (IOException e) {
      System.out.println("Ошибка ввода.");
      switch (JDBCUserService.currentUser.getType()) {
        case MANAGER:
          showManagerActions();
          break;
        case ADMIN:
          showAdminActions();
          break;
      }
    }
    return clientName;
  }

  public static int getFilmID() {
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    int filmID = 0;
    try {
      JDBCFilmService.getFilmsWithTickets();
      JDBCFilmService.showAllFilms();
      System.out.print("Введите номер фильма для действия: ");
      filmID = Integer.parseInt(bf.readLine());
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      switch (JDBCUserService.currentUser.getType()) {
        case MANAGER:
          showManagerActions();
          break;
        case ADMIN:
          showAdminActions();
          break;
      }
    } catch (IOException e) {
      System.out.println("Ошибка ввода данных.");
      switch (JDBCUserService.currentUser.getType()) {
        case MANAGER:
          showManagerActions();
          break;
        case ADMIN:
          showAdminActions();
          break;
      }
    }
    return filmID;
  }

  public static void addNewUser() {
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    System.out.println();
    System.out.print("Введите логин нового пользователя: ");
    try {
      String newUserName = bf.readLine();
      User user = new User(newUserName);
      if (!JDBCUserService.isSuchUser(user)) {
        user.setPassword("StartPassword");
        user.setType(UserType.USER);
        JDBCUserService.addUser(user);
        LogService.addNewUser(newUserName);
        System.out.println("Пользователь добавлен успешно!");
      } else {
        System.out.println("Пользователь с таким именем уже существует...");
      }
      System.out.println();
      showAdminActions();
    } catch (IOException e) {
      System.out.println("Ошибка ввода данных.");
      showAdminActions();
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      showAdminActions();
    }
  }

  public static void removeUser() {
    String userName = getUserName();
    if (userName == null) {
      showAdminActions();
      return;
    }
    User user = new User(userName);
    try {
      if (JDBCUserService.isSuchUser(user)) {
        JDBCUserService.deleteUser(user);
        LogService.deleteUser(userName);
        System.out.println("Пользователь успешно удален.");
      } else {
        System.out.println("Пользователя с таким именем не существует...");
      }
      System.out.println();
      showAdminActions();
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      showAdminActions();
    }

  }

  public static void updateUser() {
    String userName = getUserName();
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    if (userName == null) {
      showAdminActions();
      return;
    }
    User user = new User(userName);
    try {
      if (JDBCUserService.isSuchUser(user)) {
        user = JDBCUserService.allUsers.get(JDBCUserService.allUsers.indexOf(user));
        LogService.openEditionMenu();
        System.out.println();
        System.out.println("1. Изменить логин пользователя.");
        System.out.println("2. Сбросить пароль.");
        System.out.println("3. Вернуться в меню.");
        System.out.print("Выберите действие: ");
        int action = Integer.parseInt(bf.readLine());
        switch (action) {
          case 1:
            System.out.print("Введите новый логин: ");
            String newName = bf.readLine();
            if (!JDBCUserService.isSuchUser(new User(newName))) {
              user.setLogin(newName);
              JDBCUserService.updateUser(user);
              System.out.println("Логин успешно изменен.");
              LogService.editUser(userName, newName);
              System.out.println();
              showAdminActions();
            } else {
              System.out.println("Пользователя с таким именем существует...");
              updateUser();
            }
            break;
          case 2:
            user.setPassword(Security.getHashPassword(JDBCUserService.RESET_PASSWORD));
            JDBCUserService.updateUser(user);
            System.out.println("Пароль пользователя успешно сброшен");
            LogService.dropPassword(userName);
            System.out.println();
            showAdminActions();
            break;
          default:
            LogService.closeEditionMenu();
            showAdminActions();
            break;
        }
      } else {
        System.out.println("Пользователя с таким именем не существует...");
        showAdminActions();
      }
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      throwables.printStackTrace();
      showAdminActions();
    } catch (IOException e) {
      System.out.println("Ошибка ввода данных.");
      showAdminActions();
    }

  }

  public static void addNewFilm() {
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    System.out.println();
    System.out.print("Введите название фильма в формате \"Русское название/английское "
        + "название (год выпуска)\": ");
    try {
      String filmName = bf.readLine();
      System.out.print("Введите дату показа фильма в формате 01.01.2001 00:00: ");
      String filmDate = bf.readLine();
      System.out.print("Введите количество стандартных билетов: ");
      int standardAmount = Integer.parseInt(bf.readLine());
      System.out.print("Введите количество VIP-билетов: ");
      int vipAmount = Integer.parseInt(bf.readLine());
      JDBCFilmService.addNewMovie(filmName, filmDate);
      JDBCTicketService.addNewMovieTickets(standardAmount, vipAmount);
      LogService.addNewMovie(filmName, standardAmount, vipAmount);
      System.out.println("Фильм " + filmName + " доступен для клиентов.");
      showAdminActions();
    } catch (IOException e) {
      System.out.println("Ошибка ввода.");
      showAdminActions();
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      showAdminActions();
    }
  }

  public static void addAdditionalTickets() {
    Scanner scanner = new Scanner(System.in);
    System.out.println();
    try {
      JDBCFilmService.films = JDBCFilmService.getAllFilms();
      JDBCFilmService.showAllFilms();
      System.out.print("Введите номер фильма, для которого необходимо добавить билеты: ");
      int number = scanner.nextInt();
      if (JDBCFilmService.films.containsKey(number)) {
        System.out.print("Введите количество стандартных билетов: ");
        int standardAmount = scanner.nextInt();
        System.out.print("Введите количество VIP-билетов: ");
        int vipAmount = scanner.nextInt();
        JDBCTicketService.addAdditionalTickets(standardAmount, vipAmount, number);
        LogService
            .addAdditionalTickets(JDBCFilmService.films.get(number), standardAmount, vipAmount);
        System.out
            .println("Новые билеты в количестве " + (standardAmount + vipAmount) + " доступны.");
      } else {
        System.out.println("Введен неверный номер фильма.");
      }
      System.out.println();
      showAdminActions();
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      showAdminActions();
    }
  }

  public static void removeFilm() {
    Scanner scanner = new Scanner(System.in);
    System.out.println();
    try {
      JDBCFilmService.films = JDBCFilmService.getAllFilms();
      JDBCFilmService.showAllFilms();
      System.out
          .print("Введите номер фильма, который необходимо убрать из доступных к просмотру: ");
      int number = scanner.nextInt();
      if (JDBCFilmService.films.containsKey(number)) {
        JDBCFilmService.deleteMovie(number);
        LogService.removeFilm(JDBCFilmService.films.get(number));
        System.out.println(JDBCFilmService.films.get(number) + " был удален.");
      } else {
        System.out.println("Введен неверный номер фильма.");
      }
      System.out.println();
      showAdminActions();
    } catch (SQLException throwables) {
      System.out.println("Ошибка соединения с базой.");
      showAdminActions();
    }
  }


}



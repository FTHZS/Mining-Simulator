import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Main {
  private static User user;
  static {
    user = null;
  }

  public static void save(){
    Mineral.setInventory();
    user.setInventory(Mineral.getInventory());
    FileHandler.saveUser(user);
  }

  private static void printName() {
    System.out.println("\u001B[4m\u001B[1mMINING SIMULATOR\u001B[0m");
  }

  private static void PrintMenu(User user) {
    printName();
    if (user != null) {
      System.out.println("\u001B[40mAccount: [" + user.getUsername() + "]\n");
    }
    System.out.println("Event active: " + Mineral.getEventActive());
    System.out.println("Event blocks left: " + Mineral.getEventBlocksLeft() + "\u001B[0m");
    System.out.println("--------------------");
    System.out.println("What would you like to do?");
    System.out.println("1. Mine");
    System.out.println("2. Index");
    System.out.println("3. Shop");
    System.out.println("4. Inventory");
    System.out.println("5. Account Stats");
    System.out.println("6. Settings");
    System.out.println("7. Quit/Save");
    System.out.println("--------------------");
  }

  public static User getUser() {
    return user;
  }

  private static void settings() {
    System.out.println("\u001B[4mSettings\u001B[0m");
    System.out.println("1. Dialogue has delay?: "+Mineral.getToggledelay());
    System.out.println("2. Change username");
    System.out.println("3. Change password");
    System.out.println("4. Logout");
    System.out.println("5. Back to menu");

    System.out.println("\nEnter your choice: ");
  }

  public static void main(String[] args) {
    for (int i = 0; i < 256; i++){
      //System.out.println("\u001B[38;5;"+i+"m"+"#"+i+"\u001B[0m");
      //System.out.println("\u001B[48;5;"+i+"m"+"#"+i+"\u001B[0m");
    }

    
    Scanner sc = new Scanner(System.in);
    // User user = null;
    int actions = 0;

    printName();
    System.out.println("choose: \n1.Login\n2.Sign up\n");
    while (user == null) {
      String choice = sc.next();
      if (actions > 0) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        printName();
        System.out.println("choose: \n1.Login\n2.Sign up\n");
      }
      if (choice.equals("1")) {
        System.out.println("Enter Username: ");
        String Username = sc.next();
        User temp = FileHandler.getUser(Username);
        if (temp != null) {
          System.out.println("Enter Password: ");
          String Password = sc.next();
          if (temp.getPassword().equals(Password)) {
            user = FileHandler.getUser(Username);
          } else {
            System.out.println("Incorrect password! Try again.");
          }
        } else {
          System.out.println("User not found! Try again.");
        }
      } else if (choice.equals("2")) {
        System.out.println("Enter Username: ");
        String Username = sc.next();
        System.out.println("Enter Password: ");
        String Password = sc.next();
        user = new User(Username, Password, LocalDate.now().toString(), 0, 0, new ArrayList<Object[]>() {});
        FileHandler.writeUser(user);
        user.setLineNo(FileHandler.getUser(user.getUsername()).getLineNo());
      } else {
        System.out.println("Invalid choice!");
      }
      actions++;
    }
    actions = 0;
    System.out.print("\033[H\033[2J");
    System.out.flush();

    Mineral.setInventory(user.getInventory());
    Mineral.setToggleDelay(user.gettoggleDelay());

    Main.PrintMenu(user);
    while (true) {
      System.out.print("\nEnter your choice: ");
      String choice = sc.next();
      if (actions > 0) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        Main.PrintMenu(user);
      }

      if (choice.equals("1")) {
        //for (int i = 1; i<500000; i++){
          Mineral.Mine();
          user.setBlocksMined(user.getBlocksMined() + 1);
        //}
        // Mineral.appendEventsActivated(user,1);
      } else if (choice.equals("2")) {
        Mineral.printIndex();
      } else if (choice.equals("3")) {
        System.out.println("Method not implemented yet!");
      } else if (choice.equals("4")) {
        Mineral.printInventory();
      } else if (choice.equals("5")) {
        user.setRarestOreDiscovered(Mineral.getRarestOreDiscovered(user.getInventory()));
        System.out.println(user.toFileFormat(false));
      } else if (choice.equals("6")) {
        settings();
        int c = sc.nextInt();
        if (c == 1) {
          Mineral.toggledelay();
        }
        System.out.print("Going back to menu...\n");
      } else if (choice.equals("7")) {
        Mineral.setInventory();
        user.setInventory(Mineral.getInventory());
        FileHandler.saveUser(user);
        sc.close();
        break;
      } else {
        System.out.println("Invalid choice!");
      }

      actions++;
    }
  }
}
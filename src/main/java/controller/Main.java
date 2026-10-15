package controller;

import beans.UserBean;
import gui.JLogin;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import services.BackupService;
import services.NewUserService;
import util.Utilities;

public final class Main {
  private Main() {}

  public static void main(String[] args) {
    if (args.length == 0) {
      new JLogin();
      return;
    }
    if ("New".equals(args[0])) {
      createUserInteractive();
    } else if ("Flush".equals(args[0])) {
      try {
        BackupService.flush();
        System.out.println("Cleared task files in " + Utilities.getLocation());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Usage: java -jar Tasking.jar [New|Flush]");
    }
  }

  private static void createUserInteractive() {
    try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
      UserBean newUser = new UserBean();
      System.out.println("Enter ID (8 digits, PeopleSoft style):");
      newUser.setId(scanner.nextLine().trim());
      System.out.println("Enter Password:");
      newUser.setPassword(scanner.nextLine());
      System.out.println("Enter Name:");
      newUser.setName(scanner.nextLine().trim());
      System.out.println("Enter Manager ID:");
      newUser.setManager(scanner.nextLine().trim());
      if (NewUserService.createUser(newUser)) {
        System.out.println("Created user " + newUser.getId());
      }
    }
  }
}

import alpha.AlphaVantage;
import controller.StockController;
import view.StockView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    StockView view = new StockView();
    AlphaVantage alphaVantage = new AlphaVantage("DQNQNXQ2YNKIW9E0", view);
    StockController controller = new StockController(view, alphaVantage);

    while (true) {
      System.out.println("Stock Market Application Menu:");
      System.out.println("1. Examine gain/loss of a stock over a period");
      System.out.println("2. Examine x-day moving average of a stock");
      System.out.println("3. Determine x-day crossovers for a stock");
      System.out.println("4. Create and evaluate a portfolio");
      System.out.println("5. Exit");
      System.out.print("Enter your choice: ");

      int choice = scanner.nextInt();
      scanner.nextLine();  // Consume newline

      switch (choice) {
        case 1:
          System.out.print("Enter stock symbol: ");
          String symbol1 = scanner.nextLine();
          System.out.print("Enter start date (yyyy-mm-dd): ");
          LocalDate startDate1 = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          System.out.print("Enter end date (yyyy-mm-dd): ");
          LocalDate endDate1 = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          controller.examineGainOrLoss(symbol1, startDate1, endDate1);
          break;
        case 2:
          System.out.print("Enter stock symbol: ");
          String symbol2 = scanner.nextLine();
          System.out.print("Enter date (yyyy-mm-dd): ");
          LocalDate date2 = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          System.out.print("Enter x value: ");
          int x2 = scanner.nextInt();
          scanner.nextLine();  // Consume newline
          controller.examineMovingAverage(symbol2, date2, x2);
          break;
        case 3:
          System.out.print("Enter stock symbol: ");
          String symbol3 = scanner.nextLine();
          System.out.print("Enter start date (yyyy-mm-dd): ");
          LocalDate startDate3 = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          System.out.print("Enter end date (yyyy-mm-dd): ");
          LocalDate endDate3 = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          System.out.print("Enter x value: ");
          int x3 = scanner.nextInt();
          scanner.nextLine();  // Consume newline
          controller.determineCrossovers(symbol3, startDate3, endDate3, x3);
          break;
        case 4:
          System.out.print("Enter portfolio name: ");
          String portfolioName = scanner.nextLine();
          controller.createPortfolio(portfolioName);
          boolean managingPortfolio = true;
          while (managingPortfolio) {
            System.out.println("1. Add stock to portfolio");
            System.out.println("2. Calculate portfolio value");
            System.out.println("3. Back to main menu");
            System.out.print("Enter your choice: ");
            int portfolioChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (portfolioChoice) {
              case 1:
                System.out.print("Enter stock symbol: ");
                String symbol4 = scanner.nextLine();
                System.out.print("Enter number of shares: ");
                int shares = scanner.nextInt();
                scanner.nextLine();  // Consume newline
                System.out.print("Enter date of purchase (yyyy-mm-dd): ");
                LocalDate purchaseDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                controller.addStockToPortfolio(portfolioName, symbol4, shares, purchaseDate);
                break;
              case 2:
                System.out.print("Enter calculation date (yyyy-mm-dd): ");
                LocalDate calculationDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                double portfolioValue = controller.getPortfolioValueOnDate(portfolioName, calculationDate);
                view.displayPortfolioValue(portfolioName, portfolioValue);
                break;
              case 3:
                managingPortfolio = false;
                break;
              default:
                System.out.println("Invalid choice. Please try again.");
                break;
            }
          }
          break;
        case 5:
          System.out.println("Exiting...");
          scanner.close();
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}

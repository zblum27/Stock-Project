package view;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * stock view class that implements interface.
 */
public class StockView implements StockViewInterface{
  /**
   * displays current messages.
   * @param message what messages to show
   */

  public void displayMessage(String message) {
    System.out.println(message);
  }

  /**
   * displays the gain or loss.
   * @param ticker stock symbol ie NVDA or GOOG
   * @param gainOrLoss gain or loss in the form of dollars
   */
  public void displayGainOrLoss(String ticker, double gainOrLoss) {
    String result = String.format("The gain/loss for %s is $%.2f", ticker, gainOrLoss);
    displayMessage(result);
  }

  /**
   *  displays the moving average.
   * @param ticker stock symbol ie NVDA or GOOG
   * @param date date to start the calculation
   * @param days number of days after start date
   * @param average the average
   */
  public void displayMovingAverage(String ticker, LocalDate date, int days, double average) {
    String result = String.format("The %d-day moving average for %s as of %s is $%.2f", days, ticker, date.toString(), average);
    displayMessage(result);
  }

  /**
   * displays the crossover.
   * @param ticker stock symbol ie NVDA or GOOG
   * @param crossovers calculated crossover
   */
  public void displayCrossovers(String ticker, List<LocalDate> crossovers) {
    if (crossovers.isEmpty()) {
      displayMessage("No crossovers found for " + ticker);
    } else {
      String result = "Crossover dates for " + ticker + ": " + crossovers.stream()
              .map(LocalDate::toString)
              .collect(Collectors.joining(", "));
      displayMessage(result);
    }
  }

  /**
   * displays the portfolio value.
   * @param portfolioName portfolio name
   * @param value porfolio value
   */
  public void displayPortfolioValue(String portfolioName, double value) {
    String result = String.format("The current value of portfolio '%s' is $%.2f", portfolioName, value);
    displayMessage(result);
  }
}
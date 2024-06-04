package view;

import java.time.LocalDate;
import java.util.List;

public interface StockViewInterface {
  /**
   * Displays current messages.
   * @param message the message to show
   */
  void displayMessage(String message);

  /**
   * Displays the gain or loss.
   * @param ticker stock symbol (e.g., NVDA or GOOG)
   * @param gainOrLoss gain or loss in dollars
   */
  void displayGainOrLoss(String ticker, double gainOrLoss);

  /**
   * Displays the moving average.
   * @param ticker stock symbol (e.g., NVDA or GOOG)
   * @param date date to start the calculation
   * @param days number of days after the start date
   * @param movingAverage the average
   */
  void displayMovingAverage(String ticker, LocalDate date, int days, double movingAverage);

  /**
   * Displays the crossover dates.
   * @param ticker stock symbol (e.g., NVDA or GOOG)
   * @param crossoverDates calculated crossover dates
   */
  void displayCrossovers(String ticker, List<LocalDate> crossoverDates);

  /**
   * Displays the portfolio value.
   * @param portfolioName portfolio name
   * @param value portfolio value
   */
  void displayPortfolioValue(String portfolioName, double value);
}
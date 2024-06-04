package model;

import java.time.LocalDate;
import java.util.Map;

/**
 * interface for portfolio.
 */
public interface PortfolioInterface {
  /**
   * adds a stock to the portfolio.
   *
   * @param stock    stock symbol ie NVDA or GOOG
   * @param quantity number of shares to add
   */
  void addStock(StockInterface stock, int quantity, LocalDate date);

  /**
   * gets the value of the portfolio on a specific date.
   *
   * @param date date to calculate the value at
   * @return the vale
   */
  double getValueOnDate(LocalDate date);
}
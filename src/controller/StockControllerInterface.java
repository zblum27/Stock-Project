package controller;

import java.time.LocalDate;

/**
 * Creates the interface for stock controller.
 */
public interface StockControllerInterface {
  void examineGainOrLoss(String ticker, LocalDate startDate, LocalDate endDate);

  void examineMovingAverage(String ticker, LocalDate date, int days);

  void determineCrossovers(String ticker, LocalDate startDate, LocalDate endDate, int days);

  void createPortfolio(String portfolioName);

  void addStockToPortfolio(String portfolioName, String ticker, int quantity, LocalDate purchaseDate);

  double getPortfolioValueOnDate(String portfolioName, LocalDate date);
}
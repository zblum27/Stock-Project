package controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import alpha.AlphaVantageInterface;
import model.Portfolio;
import model.PortfolioInterface;
import model.Stock;
import model.StockInterface;
import view.StockViewInterface;
import model.StockData;

public class StockController implements StockControllerInterface {
  private StockViewInterface view;
  private AlphaVantageInterface alphaVantageAPI;
  private Map<String, PortfolioInterface> portfolios;

  public StockController(StockViewInterface view, AlphaVantageInterface alphaVantageAPI) {
    this.view = view;
    this.alphaVantageAPI = alphaVantageAPI;
    this.portfolios = new HashMap<>();
  }

  @Override
  public void examineGainOrLoss(String ticker, LocalDate startDate, LocalDate endDate) {
    StockData startData = alphaVantageAPI.getStockDataOnDate(ticker, startDate);
    StockData endData = alphaVantageAPI.getStockDataOnDate(ticker, endDate);

    if (startData == null || endData == null) {
      view.displayMessage("Stock data not available for the given dates.");
      return;
    }

    double startPrice = startData.getClosePrice();
    double endPrice = endData.getClosePrice();
    view.displayGainOrLoss(ticker, endPrice - startPrice);
  }

  @Override
  public void examineMovingAverage(String ticker, LocalDate date, int days) {
    Map<LocalDate, StockData> stockData = alphaVantageAPI.getStockData(ticker);
    double sum = 0;
    int actualDays = 0;

    for (int i = 0; i < days; i++) {
      StockData dailyData = stockData.get(date.minusDays(i));
      if (dailyData != null) {
        sum += dailyData.getClosePrice();
        actualDays++;
      }
    }

    if (actualDays == 0) {
      view.displayMessage("No data available to calculate moving average.");
      return;
    }

    view.displayMovingAverage(ticker, date, days, sum / actualDays);
  }

  @Override
  public void determineCrossovers(String ticker, LocalDate startDate, LocalDate endDate, int days) {
    Map<LocalDate, StockData> stockData = alphaVantageAPI.getStockData(ticker);
    List<LocalDate> crossovers = new ArrayList<>();
    double previousMovingAverage = 0;

    for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
      double movingAverage = 0;
      int count = 0;

      for (int i = 0; i < days; i++) {
        StockData dailyData = stockData.get(date.minusDays(i));
        if (dailyData != null) {
          movingAverage += dailyData.getClosePrice();
          count++;
        }
      }

      if (count == 0) continue;

      movingAverage /= count;

      // Check for crossover
      if ((stockData.get(date).getClosePrice() > movingAverage && previousMovingAverage <= movingAverage) ||
              (stockData.get(date).getClosePrice() < movingAverage && previousMovingAverage >= movingAverage)) {
        crossovers.add(date);
      }

      previousMovingAverage = movingAverage;
    }

    if (crossovers.isEmpty()) {
      view.displayMessage("No crossovers found.");
      return;
    }

    view.displayCrossovers(ticker, crossovers);
  }

  @Override
  public void createPortfolio(String portfolioName) {
    portfolios.put(portfolioName, new Portfolio(portfolioName));
  }

  @Override
  public void addStockToPortfolio(String portfolioName, String ticker, int quantity, LocalDate purchaseDate) {
    PortfolioInterface portfolio = portfolios.get(portfolioName);
    StockInterface stock = new Stock(ticker, quantity, purchaseDate, alphaVantageAPI);
    portfolio.addStock(stock, quantity, purchaseDate);
  }

  @Override
  public double getPortfolioValueOnDate(String portfolioName, LocalDate date) {
    return portfolios.get(portfolioName).getValueOnDate(date);
  }
}
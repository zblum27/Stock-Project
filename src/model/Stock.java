package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import alpha.AlphaVantageInterface;

public class Stock implements StockInterface {
  private String symbol;
  private Map<LocalDate, Integer> sharesMap;
  private AlphaVantageInterface alphaVantage;

  public Stock(String symbol, int shares, LocalDate purchaseDate, AlphaVantageInterface alphaVantage) {
    this.symbol = symbol;
    this.sharesMap = new HashMap<>();
    this.sharesMap.put(purchaseDate, shares);
    this.alphaVantage = alphaVantage;
  }

  @Override
  public String getSymbol() {
    return symbol;
  }

  @Override
  public void addShares(int shares, LocalDate purchaseDate) {
    sharesMap.put(purchaseDate, sharesMap.getOrDefault(purchaseDate, 0) + shares);
  }

  @Override
  public double getValueOnDate(LocalDate date) {
    int totalShares = sharesMap.entrySet().stream()
            .filter(entry -> !entry.getKey().isAfter(date))
            .mapToInt(Map.Entry::getValue)
            .sum();
    double priceOnDate = fetchPriceOnDate(symbol, date);
    return totalShares * priceOnDate;
  }

  @Override
  public void displayStock() {
    System.out.println("Stock: " + symbol);
    for (Map.Entry<LocalDate, Integer> entry : sharesMap.entrySet()) {
      System.out.println("  Date: " + entry.getKey() + " Shares: " + entry.getValue());
    }
  }

  private double fetchPriceOnDate(String symbol, LocalDate date) {
    StockData stockData = alphaVantage.getStockDataOnDate(symbol, date);
    return stockData.getClosePrice();
  }
}

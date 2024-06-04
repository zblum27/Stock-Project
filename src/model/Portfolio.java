package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import alpha.AlphaVantageInterface;

public class Portfolio implements PortfolioInterface {
  private String name;
  private Map<String, StockInterface> stocks;

  public Portfolio(String name) {
    this.name = name;
    this.stocks = new HashMap<>();
  }

  public String getName() {
    return name;
  }

  @Override
  public void addStock(StockInterface stock, int quantity, LocalDate date) {
    String symbol = stock.getSymbol();
    if (stocks.containsKey(symbol)) {
      StockInterface existingStock = stocks.get(symbol);
      existingStock.addShares(quantity, date);
    } else {
      stocks.put(symbol, stock);
    }
  }

  @Override
  public double getValueOnDate(LocalDate date) {
    double totalValue = 0;
    for (StockInterface stock : stocks.values()) {
      totalValue += stock.getValueOnDate(date);
    }
    return totalValue;
  }

  public void displayPortfolio() {
    System.out.println("Portfolio: " + name);
    for (StockInterface stock : stocks.values()) {
      stock.displayStock();
    }
  }
}

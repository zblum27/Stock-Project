package model;

import java.time.LocalDate;

public interface StockInterface {
  String getSymbol();
  void addShares(int shares, LocalDate purchaseDate);
  double getValueOnDate(LocalDate date);
  void displayStock();
}

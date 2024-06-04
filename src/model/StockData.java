package model;

public class StockData {
  private double openPrice;
  private double highPrice;
  private double lowPrice;
  private double closePrice;
  private double volume;

  public StockData(double openPrice, double highPrice, double lowPrice, double closePrice, double volume) {
    this.openPrice = openPrice;
    this.highPrice = highPrice;
    this.lowPrice = lowPrice;
    this.closePrice = closePrice;
    this.volume = volume;
  }

  public double getOpenPrice() {
    return openPrice;
  }

  public double getHighPrice() {
    return highPrice;
  }

  public double getLowPrice() {
    return lowPrice;
  }

  public double getClosePrice() {
    return closePrice;
  }

  public double getVolume() {
    return volume;
  }
}
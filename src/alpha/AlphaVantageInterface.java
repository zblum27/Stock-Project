package alpha;

import java.time.LocalDate;
import java.util.Map;

import model.StockData;

public interface AlphaVantageInterface {
  Map<LocalDate, StockData> getStockData(String symbol);
  Map<LocalDate, StockData> getStockData(String symbol, String startDate, String endDate);
  StockData getStockDataOnDate(String symbol, LocalDate date);
}
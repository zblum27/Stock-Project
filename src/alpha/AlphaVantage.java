package alpha;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.StockData;
import view.StockViewInterface;

public class AlphaVantage implements AlphaVantageInterface {
  private String apiKey;
  private StockViewInterface view;

  public AlphaVantage(String apiKey, StockViewInterface view) {
    this.apiKey = apiKey;
    this.view = view;
  }

  @Override
  public Map<LocalDate, StockData> getStockData(String symbol) {
    return getStockData(symbol, null, null);
  }

  @Override
  public Map<LocalDate, StockData> getStockData(String symbol, String startDate, String endDate) {
    Map<LocalDate, StockData> stockDataMap = new HashMap<>();
    URL url = null;
    try {
      url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol=" + symbol
              + "&apikey=" + apiKey
              + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("The AlphaVantage API has either changed or no longer works");
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    try (InputStream in = url.openStream(); Scanner scanner = new Scanner(in)) {
      scanner.nextLine(); // Skip header
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] data = line.split(",");
        LocalDate date = LocalDate.parse(data[0], formatter);
        double openPrice = Double.parseDouble(data[1]);
        double highPrice = Double.parseDouble(data[2]);
        double lowPrice = Double.parseDouble(data[3]);
        double closePrice = Double.parseDouble(data[4]);
        double volume = Double.parseDouble(data[5]);

        if ((startDate == null || date.compareTo(LocalDate.parse(startDate, formatter)) >= 0) &&
                (endDate == null || date.compareTo(LocalDate.parse(endDate, formatter)) <= 0)) {
          StockData stockData = new StockData(openPrice, highPrice, lowPrice, closePrice, volume);
          stockDataMap.put(date, stockData);
          view.displayMessage("Added data for date: " + date + " with close price: " + closePrice);
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + symbol);
    }

    view.displayMessage("Fetched stock data for symbol: " + symbol + " size: " + stockDataMap.size());
    return stockDataMap;
  }

  @Override
  public StockData getStockDataOnDate(String symbol, LocalDate date) {
    Map<LocalDate, StockData> stockDataMap = getStockData(symbol);
    while (!stockDataMap.containsKey(date)) {
      date = date.minusDays(1);
    }
    return stockDataMap.get(date);
  }
}
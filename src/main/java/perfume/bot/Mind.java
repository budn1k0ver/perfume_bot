package perfume.bot;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Mind {

  private static final Logger log = Logger.getLogger(Mind.class);
  private static final String PROPERTIES_FILE_NAME = "application.properties";
  private static Properties properties;

  public static void main(String[] args) {
    loadProperties();
    
    String token = properties.getProperty("token");
    String name = properties.getProperty("name");
    try {
      var botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(new PerfumeAnalyzerBot(token, name));
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }


  private static void loadProperties() {
    try (var in = Mind.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE_NAME)) {
      properties = new Properties();

      if (in == null) {
        log.warn("Unable to find " + PROPERTIES_FILE_NAME);
        return;
      }

      properties.load(in);
    } catch(IOException ex) {
      log.error(ex.getMessage(), ex);
      System.exit(1);
    }
  }
}

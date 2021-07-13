package perfume.bot;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Mind {

  private static final Logger log = Logger.getLogger(Mind.class);
  
  public static void main(String[] args) {
    try {
      var botsApi = new TelegramBotsApi(DefaultBotSession.class);
      // botsApi.registerBot(new PerfumeBot());
      botsApi.registerBot(new HelloBot());
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
  
}

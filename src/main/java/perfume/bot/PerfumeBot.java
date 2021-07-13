package perfume.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PerfumeBot extends TelegramLongPollingBot {

  @Override
  public void onUpdateReceived(Update update) {
    if(update.hasMessage() && update.getMessage().hasText()) {
      var message = new SendMessage();
      message.setChatId(update.getMessage().getChatId().toString());
      message.setText(update.getMessage().getText());

      try {
        execute(message);
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public String getBotUsername() {
    return "natala_perfume_bot";
  }

  @Override
  public String getBotToken() {
    return "1890397114:AAEAsRlqICZoxxGZeXEgpAgomHx5BdIOwkM";
  }

  
  
}

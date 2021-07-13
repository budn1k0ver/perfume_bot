package perfume.bot;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.toggle.BareboneToggle;
import org.telegram.abilitybots.api.util.AbilityUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HelloBot extends AbilityBot {

  private static final BareboneToggle toggle = new BareboneToggle();
  private static final String TOKEN = "1890397114:AAEAsRlqICZoxxGZeXEgpAgomHx5BdIOwkM";
  private static final String USER_NAME = "natala_perfume_bot";
  

  protected HelloBot() {
    super(TOKEN, USER_NAME, toggle);
  }

  public Ability sayHello() {
    return Ability.builder()
      .name("hello")
      .info("say hello!")
      .locality(Locality.ALL)
      .privacy(Privacy.PUBLIC)
      .action(ctx -> silent.send("Hello my majesty!", ctx.chatId()))
      .build();
  }

  // public Reply sayYuckOnImage() {
  //   BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> silent.send("Yuck", AbilityUtils.getChatId(upd));
  //   return Reply.of(action, Flag.PHOTO);
  // }

  public Ability playWithMe() {
    final var playMessage = "Play with me!";

    return Ability.builder()
      .name("play")
      .info("Do you to play with me?")
      .privacy(Privacy.PUBLIC)
      .locality(Locality.ALL)
      .input(0)
      .action(ctx -> {
          var msg = new SendMessage();
          msg.setText(playMessage);
          msg.setChatId(Long.toString(ctx.chatId()));
          msg.setReplyMarkup(new ForceReplyKeyboard(true));
          silent.execute(msg);
          // silent.forceReply(playMessage, ctx.chatId());
        })
      .reply((bot, upd) -> {
          System.out.println("i'm in a reply");
          silent.send("It's been nice playing with you!", upd.getMessage().getChatId());
        }, 
        Flag.MESSAGE,
        Flag.REPLY,
        isReplyBot(),
        isReplyToMessage(playMessage)
      )
      .build();
  }

  private Predicate<Update> isReplyBot() {
    return upd -> {
      return upd.getMessage().getReplyToMessage().getFrom().getUserName().equalsIgnoreCase(getBotUsername());
    };
  }

  private Predicate<Update> isReplyToMessage(String message) {
    return upd -> {
      var reply = upd.getMessage().getReplyToMessage();
      return reply.hasText() && reply.getText().equalsIgnoreCase(message);
    };
  }

  @Override
  public long creatorId() {
    return 179281181;
  }
  
}

package perfume.bot;

import java.io.File;
import java.util.Arrays;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class PerfumeAnalyzerBot extends AbilityBot {

  private static final Logger log = LoggerFactory.getLogger(PerfumeAnalyzerBot.class);
  private static final String CLOTHES_Q = "Выберите какой из образов Вам ближе, что бы хотели примерить на себя?"; 

  public PerfumeAnalyzerBot(String token, String name) {
    super(token, name);
  }

  public Ability selectionOfPerfume() {
    return Ability.builder()
      .name("personal_perfume")
      .privacy(Privacy.PUBLIC)
      .locality(Locality.ALL)
      .action(ctx -> {
        silent.send(CLOTHES_Q, ctx.chatId());


        InputMediaPhoto p1 = new InputMediaPhoto();
        p1.setMedia(new File("/Users/bart/project_azure/bots/perfume/src/main/resources/images/clothes/test.jpeg"), "A");
        InputMediaPhoto p2 = new InputMediaPhoto();
        p2.setMedia(new File("/Users/bart/project_azure/bots/perfume/src/main/resources/images/clothes/test copy.jpeg"), "B");
        InputMediaPhoto p3 = new InputMediaPhoto();
        p3.setMedia(new File("/Users/bart/project_azure/bots/perfume/src/main/resources/images/clothes/test copy 2.jpeg"), "C");
        
        
        SendMediaGroup options = SendMediaGroup.builder()
          .medias(Arrays.asList(p1,p2,p3))
          .chatId(String.valueOf(ctx.chatId()))
          .build();
        try {
          execute(options);
        } catch (TelegramApiException e) {
          log.error("Could not execute bot API method", e);
        }
      }).build();
  }

  @Override
  public long creatorId() {
    return 179281181;
  }

  public ReplyFlow directionFlow() {
    var saidLeft = Reply.of((bot, upd) -> silent.send("Sir, I have gone left", getChatId(upd)), hasMessageWith("go left or else"));

    var leftFlow = ReplyFlow.builder(db)
      .action((bot, upd) -> silent.send("I don't know how to go left.", getChatId(upd)))
      .onlyIf(hasMessageWith("left"))
      .next(saidLeft).build();

    var sideRight = Reply.of((bot, upd) -> silent.send("Sir, I have gone right.", getChatId(upd)), hasMessageWith("right"));

    return ReplyFlow.builder(db)
      .action((bot, upd) -> silent.send("Command me to go left or right.", getChatId(upd)))
      .onlyIf(hasMessageWith("wake up"))
      .next(leftFlow)
      .next(sideRight)
      .build();
  }

  private Predicate<Update> hasMessageWith(String msg) {
    return upd -> upd.getMessage().getText().equalsIgnoreCase(msg);
  }
}

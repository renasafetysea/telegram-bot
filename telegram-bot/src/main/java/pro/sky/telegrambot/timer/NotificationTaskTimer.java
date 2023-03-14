package pro.sky.telegrambot.timer;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.NotificationTaskService;

import java.util.concurrent.TimeUnit;

@Component
public class NotificationTaskTimer {
    private final NotificationTaskService notificationTaskService;
    private final TelegramBot telegramBot;

    public NotificationTaskTimer(NotificationTaskService notificationTaskService, TelegramBot telegramBot) {
        this.notificationTaskService = notificationTaskService;
        this.telegramBot = telegramBot;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void checkNotifications(){
        notificationTaskService.findNotificationForSend().forEach(notificationTask -> {
            telegramBot.execute(
                    new SendMessage(notificationTask.getUserId(),"Вы просили напомнить об этом!" + notificationTask.getMessage())
            );
            notificationTaskService.deleteTask(notificationTask);
        });
    }
}

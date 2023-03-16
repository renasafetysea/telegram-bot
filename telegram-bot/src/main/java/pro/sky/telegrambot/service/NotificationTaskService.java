package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public void addNotificationTask(LocalDateTime localDateTime, String message, Long userId){
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setNotificationDateTime(localDateTime);
        notificationTask.setMessage(message);
        notificationTask.setUserId(userId);
        notificationTaskRepository.save(notificationTask);
    }
    public List<NotificationTask> findNotificationForSend(){
        return notificationTaskRepository.findNotificationTasksByNotificationDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }
    public void deleteTask(NotificationTask notificationTask) {
        notificationTaskRepository.delete(notificationTask);

    }
}

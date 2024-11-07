import java.util.List;

public interface NotificationService {
    Notification sendNotification(String message, String recipient);
    List<Notification> getNotificationsForUser(String recipient);
}
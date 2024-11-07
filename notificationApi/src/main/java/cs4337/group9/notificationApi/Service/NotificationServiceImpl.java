import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private List<Notification> notifications = new ArrayList<>();

    @Override
    public Notification sendNotification(String message, String recipient) {
        Notification notification = new Notification(message, recipient);
        notification.setId(UUID.randomUUID().toString());  // Generate a unique ID
        notifications.add(notification);
        return notification;
    }

    @Override
    public List<Notification> getNotificationsForUser(String recipient) {
        return notifications.stream()
                .filter(notification -> notification.getRecipient().equals(recipient))
                .collect(Collectors.toList());
    }
}
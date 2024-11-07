import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Send a notification
    @PostMapping
    public Notification sendNotification(@RequestParam String message, @RequestParam String recipient) {
        return notificationService.sendNotification(message, recipient);
    }

    // Get all notifications for a specific user
    @GetMapping("/{recipient}")
    public List<Notification> getNotificationsForUser(@PathVariable String recipient) {
        return notificationService.getNotificationsForUser(recipient);
    }
}
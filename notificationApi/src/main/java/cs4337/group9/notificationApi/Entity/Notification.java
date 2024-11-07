import java.time.LocalDateTime;

public class Notification {
    private String id;
    private String message;
    private String recipient;  // Could be user ID or username
    private LocalDateTime timestamp;

    public Notification(String message, String recipient) {
        this.message = message;
        this.recipient = recipient;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
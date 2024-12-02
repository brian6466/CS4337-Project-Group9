package cs4337.group9.contentapi.Service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShareService {

    public String generateFacebookShareLink(String articleUrl) {
        return "https://www.facebook.com/sharer/sharer.php?u=" + articleUrl;
    }

    private final String BASE_URL = "http://localhost:8081/article/";

    public String generateShareLink(String platform, UUID articleId) {
        String articleUrl = BASE_URL + articleId; // API URL (or frontend URL in the future)

        return switch (platform.toUpperCase()) {
            case "FACEBOOK" -> "https://www.facebook.com/sharer/sharer.php?u=" + articleUrl;
            case "TWITTER" -> "https://twitter.com/intent/tweet?url=" + articleUrl;
            default -> throw new IllegalArgumentException("Unsupported platform: " + platform);
        };
    }
}

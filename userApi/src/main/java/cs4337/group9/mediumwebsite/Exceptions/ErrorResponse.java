package cs4337.group9.mediumwebsite.Exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private String resource;
    private String details;
    private String timestamp;

    public ErrorResponse(HttpStatus status, String message, String resource, String details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.resource = resource;
        this.timestamp = Instant.now().toString();
    }
}



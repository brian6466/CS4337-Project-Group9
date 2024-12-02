package cs4337.group9.mediumwebsite.Exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String resourceId;

    // Manually defined constructor to pass the message to RuntimeException's constructor
    public ResourceNotFoundException(String resourceName, String resourceId) {
        super(resourceName + " with ID " + resourceId + " not found");
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }
}

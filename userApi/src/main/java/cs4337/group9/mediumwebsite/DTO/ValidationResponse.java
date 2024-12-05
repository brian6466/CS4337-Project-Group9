package cs4337.group9.mediumwebsite.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {
    private boolean isValid;
    private String role;
}

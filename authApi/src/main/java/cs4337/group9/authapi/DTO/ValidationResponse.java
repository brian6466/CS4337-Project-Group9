package cs4337.group9.authapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponse {
    private boolean isValid;
    private String role;
}

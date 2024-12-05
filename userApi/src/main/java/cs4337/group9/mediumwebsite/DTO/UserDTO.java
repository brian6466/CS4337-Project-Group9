package cs4337.group9.mediumwebsite.DTO;

import cs4337.group9.mediumwebsite.Enum.Role;
import cs4337.group9.mediumwebsite.Enum.Status;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {
    private UUID userId;
    private String username;
    private String email;
    private String about;
    private String profilePicture;
    private Role role;
    private Status status;
}

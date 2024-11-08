package cs4337.group9.mediumwebsite.Entity;
import cs4337.group9.mediumwebsite.enums.Status;
import cs4337.group9.mediumwebsite.enums.Role;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String email;
    private String password;
    private Role role;
    private Status status;


}

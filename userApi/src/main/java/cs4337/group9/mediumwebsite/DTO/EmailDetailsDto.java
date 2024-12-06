package cs4337.group9.mediumwebsite.DTO;

import lombok.Data;

@Data
public class EmailDetailsDto {
    private String recipient;
    private String emailBody;
    private String emailSubject;
}

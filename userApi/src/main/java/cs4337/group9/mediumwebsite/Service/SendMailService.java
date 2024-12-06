package cs4337.group9.mediumwebsite.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import cs4337.group9.mediumwebsite.DTO.EmailDetailsDto;

@Service
public class SendMailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendMailService.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public SendMailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendFollowNotificationMail(EmailDetailsDto details, String followerUsername) {
        try {
            LOGGER.info("Sending a follow notification email.");

            String emailBody = String.format("%s has started following you!", followerUsername);
            String emailSubject = "New Follower Notification";

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(emailBody);
            mailMessage.setSubject(emailSubject);

            javaMailSender.send(mailMessage);
        } catch (Exception exception) {
            LOGGER.error("Error while sending the mail: {}", exception.getMessage());
            throw new RuntimeException("Error while sending the mail: " + exception.getMessage());
        }
    }
}
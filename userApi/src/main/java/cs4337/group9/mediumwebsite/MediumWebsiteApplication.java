package cs4337.group9.mediumwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class MediumWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediumWebsiteApplication.class, args);
    }

    @GetMapping( "/hello" ) public Map<String, Object> sayHello () {
        return Map.of( "message" , "Hello World!" ); }
}

package cs4337.group9.mediumwebsite.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")  // Base URL for the user-related endpoints
public class UserController {

    @GetMapping()
    public String getUser() {
        return "User Returned!";
    }
}
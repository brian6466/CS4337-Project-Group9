package cs4337.group9.mediumwebsite.Controller;

import cs4337.group9.mediumwebsite.Entity.User;
import cs4337.group9.mediumwebsite.Exceptions.UserNotFoundException;
import cs4337.group9.mediumwebsite.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
    }

//    @PutMapping
//    public ResponseEntity<String> updateUser(@RequestBody User user) {
//        //userService.checkIfUserExists(user)
//        //userService.updateUser(user)
//        return new ResponseEntity<>("User updated successfully!", HttpStatus.CREATED);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<String> deleteUser(@RequestBody User user) {
//        //userService.checkIfUserExists(user)
//        //userService.deleteUser(user)
//        return new ResponseEntity<>("User deleted successfully!", HttpStatus.CREATED);
//    }
//
//    @PutMapping("/ban/{userId}")
//    public ResponseEntity<String> banUser(@PathVariable String userId) {
//        //userService.checkIfUserExists(userId)
//        //Call service ban user method
//        return new ResponseEntity<>("User banned successfully!", HttpStatus.CREATED);
//    }
//
//    @PutMapping("/unban/{userId}")
//    public ResponseEntity<String> unbanUser(@PathVariable String userId) {
//        //userService.checkIfUserExists(userId)
//        //Call service unban user method
//        return new ResponseEntity<>("User unbanned successfully!", HttpStatus.CREATED);
//    }


}
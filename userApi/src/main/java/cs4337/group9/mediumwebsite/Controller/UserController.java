package cs4337.group9.mediumwebsite.Controller;

import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cs4337.group9.mediumwebsite.Exceptions.UserNotFoundException;
import cs4337.group9.mediumwebsite.Exceptions.UserAlreadyExistsException;
import cs4337.group9.mediumwebsite.Exceptions.ResourceNotFoundException;


import java.util.List;
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
    public ResponseEntity<UserEntity> getUser(@PathVariable UUID userId) {
        UserEntity user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserEntity user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<>("User created successfully!", HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable UUID userId, @RequestBody UserEntity user) {
        userService.updateUser(userId, user);
        return new ResponseEntity<>("User updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/ban/{userId}")
    public ResponseEntity<String> banUser(@PathVariable UUID userId, @RequestParam UUID adminId) {
        userService.banUser(userId, adminId);
        return new ResponseEntity<>("User banned successfully!", HttpStatus.OK);
    }

    @PutMapping("/unban/{userId}")
    public ResponseEntity<String> unbanUser(@PathVariable UUID userId, @RequestParam UUID adminId) {
        userService.unbanUser(userId, adminId);
        return new ResponseEntity<>("User unbanned successfully!", HttpStatus.OK);
    }


}
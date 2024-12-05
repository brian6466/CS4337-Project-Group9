package cs4337.group9.mediumwebsite.Controller;

import cs4337.group9.mediumwebsite.DTO.UserDTO;
import cs4337.group9.mediumwebsite.DTO.ValidationResponse;
import cs4337.group9.mediumwebsite.Entity.UserEntity;
import cs4337.group9.mediumwebsite.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cs4337.group9.mediumwebsite.Exceptions.UserAlreadyExistsException;


import java.util.List;
import java.util.Map;
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
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID userId) {
        UserDTO user = userService.getUserDtoById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getAllUsers();
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

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        ValidationResponse response = userService.validateCredentials(email, password);
        if (response.isValid()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }

}
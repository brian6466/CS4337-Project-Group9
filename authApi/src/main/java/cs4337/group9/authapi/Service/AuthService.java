package cs4337.group9.authapi.Service;

import cs4337.group9.authapi.Client.UserApiClient;
import cs4337.group9.authapi.Util.JwtUtil;

import cs4337.group9.authapi.DTO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserApiClient userApiClient;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(String email, String password) {
        User user = userApiClient.fetchUserByEmail(email);

        System.out.println(user);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole());
    }

}

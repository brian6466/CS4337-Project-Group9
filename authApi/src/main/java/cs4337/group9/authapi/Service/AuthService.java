package cs4337.group9.authapi.Service;

import cs4337.group9.authapi.Client.UserApiClient;
import cs4337.group9.authapi.DTO.ValidationResponse;
import cs4337.group9.authapi.Util.JwtUtil;

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
        ValidationResponse validationResponse = userApiClient.validateUserCredentials(email, password);

        if (!validationResponse.isValid()) {
            throw new RuntimeException("Invalid credentials");
        }

        String role = validationResponse.getRole();

        return jwtUtil.generateToken(email, role);
    }

}

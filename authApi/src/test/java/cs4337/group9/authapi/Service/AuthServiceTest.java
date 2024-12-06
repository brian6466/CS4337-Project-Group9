package cs4337.group9.authapi.Service;

import cs4337.group9.authapi.Client.UserApiClient;
import cs4337.group9.authapi.DTO.ValidationResponse;
import cs4337.group9.authapi.Util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserApiClient userApiClient;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccess() {
        String email = "test@example.com";
        String password = "password";
        String role = "USER";
        String token = "jwtToken";

        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setValid(true);
        validationResponse.setRole(role);

        when(userApiClient.validateUserCredentials(email, password)).thenReturn(validationResponse);
        when(jwtUtil.generateToken(email, role)).thenReturn(token);

        String result = authService.login(email, password);

        assertEquals(token, result);
        verify(userApiClient, times(1)).validateUserCredentials(email, password);
        verify(jwtUtil, times(1)).generateToken(email, role);
    }

    @Test
    public void testLoginInvalidCredentials() {
        String email = "test@example.com";
        String password = "wrongPassword";

        ValidationResponse validationResponse = new ValidationResponse();
        validationResponse.setValid(false);

        when(userApiClient.validateUserCredentials(email, password)).thenReturn(validationResponse);

        Exception exception = assertThrows(RuntimeException.class, () -> authService.login(email, password));

        assertEquals("Invalid credentials", exception.getMessage());
        verify(userApiClient, times(1)).validateUserCredentials(email, password);
        verify(jwtUtil, times(0)).generateToken(anyString(), anyString());
    }
}
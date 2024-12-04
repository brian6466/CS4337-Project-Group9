package cs4337.group9.authapi.Client;

import cs4337.group9.authapi.DTO.ValidationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserApiClient {

    @Value("${user.api.url}")
    private String userApiUrl;

    @Value("${internal.secret.key}")
    private String internalSecretKey;

    private final RestTemplate restTemplate;

    public UserApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ValidationResponse validateUserCredentials(String email, String password) {
        String url = userApiUrl + "/validate";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Authorization", internalSecretKey);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<ValidationResponse> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, ValidationResponse.class);
            return response.getBody();
        } catch (Exception e) {
            ValidationResponse failedResponse = new ValidationResponse();
            failedResponse.setValid(false);
            return failedResponse;
        }
    }
}


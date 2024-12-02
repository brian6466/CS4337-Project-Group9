package cs4337.group9.authapi.Client;

import com.fasterxml.jackson.databind.ObjectMapper;
import cs4337.group9.authapi.DTO.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


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

    public User fetchUserByEmail(String email) {
        String url = userApiUrl + "/" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "*/*");

        headers.add("Authorization", internalSecretKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String jsonResponse = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonResponse, User.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping JSON to User", e);
        }
    }
}


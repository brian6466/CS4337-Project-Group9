package cs4337.group9.authapi.Client;

import cs4337.group9.authapi.DTO.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserApiClient {

    @Value("${user.api.url}")
    private String userApiUrl;

    private final RestTemplate restTemplate;

    public UserApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User fetchUserByEmail(String email) {
        String url = userApiUrl + "/" + email;
        return restTemplate.getForObject(url, User.class);
    }
}

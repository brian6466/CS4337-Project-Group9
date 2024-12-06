package cs4337.group9.mediumwebsite;

import cs4337.group9.mediumwebsite.Repostiory.AdminActionRepository;
import cs4337.group9.mediumwebsite.Repostiory.FollowRepository;
import cs4337.group9.mediumwebsite.Repostiory.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = UserApiApplication.class)
@ImportAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class UserApiApplicationTests {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private FollowRepository followRepository;

    @MockBean
    private AdminActionRepository adminActionRepository;

    @Test
    void contextLoads() {
    }

}

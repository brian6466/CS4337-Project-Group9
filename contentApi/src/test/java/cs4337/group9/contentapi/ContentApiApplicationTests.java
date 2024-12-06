package cs4337.group9.contentapi;

import cs4337.group9.contentapi.Repository.ArticleRepository;
import cs4337.group9.contentapi.Repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@ImportAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
class ContentApiApplicationTests {

    @MockBean
    private ArticleRepository articleRepository;

    @MockBean
    private CommentRepository commentRepository;



    @Test
    void contextLoads() {
    }

}

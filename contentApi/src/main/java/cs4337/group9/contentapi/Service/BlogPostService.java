package cs4337.group9.contentapi.Service;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogPostService {
    private List<BlogPost> blogPosts = new ArrayList<>();

    public List<BlogPost> getAllPosts() {
        return blogPosts;
    }

    public BlogPost createPost(BlogPost newPost) {
        newPost.setId((long) (blogPosts.size() + 1)); 
        newPost.setCreatedAt(LocalDateTime.now());
        blogPosts.add(newPost);
        return newPost;
    }
}

package cs4337.group9.contentapi.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cs4337.group9.contentapi.Model.BlogPost;
import cs4337.group9.contentapi.Service.BlogPostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    @SuppressWarnings("unused")
    @Autowired
    private BlogPostService blogPostService;

    @GetMapping
    public List<BlogPost> getAllPosts() {
        return BlogPostService.getAllPosts();
    }
}

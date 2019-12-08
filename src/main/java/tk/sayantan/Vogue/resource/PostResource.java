package tk.sayantan.Vogue.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.sayantan.Vogue.exception.UserNotFoundException;
import tk.sayantan.Vogue.model.Post;
import tk.sayantan.Vogue.service.CategoryService;
import tk.sayantan.Vogue.service.PostService;
import tk.sayantan.Vogue.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public")
public class PostResource {

    private static final Logger LOG = LoggerFactory.getLogger(PostResource.class);

    @Autowired
    private PostService service;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/posts")
    @CrossOrigin(origins = {"http://localhost:4200"})
    public List<Post> retrieveAllPosts() {
        LOG.info("> retrieveAllPosts");
        List<Post> posts = null;
        try {
            posts = service.findAllPosts();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< retrieveAllPosts");
        return posts;
    }

    @GetMapping(value = "/posts/{id}")
    @CrossOrigin(origins = {"http://localhost:4200"})
    public Post retrievePost(@PathVariable int id) {
        LOG.info("> retrievePost");
        Post retrievePost = null;
        try {
            Optional<Post> post = service.findOne(id);
            if (!post.isPresent())
                throw new UserNotFoundException("id-" + id);
            retrievePost = post.get();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< retrievePost");
        return retrievePost;
    }
}

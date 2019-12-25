package tk.sayantan.Vogue.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tk.sayantan.Vogue.exception.UserNotFoundException;
import tk.sayantan.Vogue.model.Category;
import tk.sayantan.Vogue.model.DTO;
import tk.sayantan.Vogue.model.Post;
import tk.sayantan.Vogue.model.User;
import tk.sayantan.Vogue.service.CategoryService;
import tk.sayantan.Vogue.service.PostService;
import tk.sayantan.Vogue.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authenticated")
@CrossOrigin(origins = {"https://vogue-dev.herokuapp.com"})
public class SecuredResource {

    private static final Logger LOG = LoggerFactory.getLogger(SecuredResource.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "/users")
    public List<User> retrieveAllUsers() {
        LOG.info("> retrieveAllUsers");
        List<User> users = null;
        try {
            users = userService.findAllUsers();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< retrieveAllUsers");
        return users;
    }

    @GetMapping(value = "/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        LOG.info("> retrieveUser");
        User user = null;
        try {
            Optional<User> optionalUser = userService.findOne(id);
            if (!optionalUser.isPresent())
                throw new UserNotFoundException("id-" + id);
            user = optionalUser.get();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< retrieveUser");
        return user;
    }

    @DeleteMapping(value = "/users/{id}")
    public User deleteUser(@PathVariable int id) {
        LOG.info("> deleteUser");
        User user = null;
        try {
            Optional<User> optionalUser = userService.findOne(id);
            if (!optionalUser.isPresent())
                throw new UserNotFoundException("id-" + id);
            user = optionalUser.get();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< deleteUser");
        return user;
    }

    @PutMapping(value = "/users")
    public ResponseEntity<Object> updateUser(@RequestBody User updateUser) {
        LOG.info("> updateUser");
        try {
            Optional<User> user = userService.findOne(updateUser.getId().intValue());
            if (!user.isPresent())
                throw new UserNotFoundException("id-" + updateUser.getId());
            else
                userService.save(updateUser);
        } catch (UserNotFoundException e) {
            LOG.error("User not found: {} {}", e, e.getMessage());
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< updateUser");
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/users/login/{username}")
    public User retrieveUserByUsername(@PathVariable String username) {
        LOG.info("> retrieveUserByUsername");
        User user = null;
        try {
            user = userService.findByEmail(username);
            if (user == null)
                throw new UserNotFoundException("id-" + username);
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< retrieveUserByUsername");
        return user;
    }

    @RequestMapping(value = "/users/posts/{id}", method = RequestMethod.GET)
    public List<Post> retrieveAllPostsForSpecificUser(@PathVariable int id) {
        LOG.info("> retrieveAllPostsForSpecificUser");
        List<Post> posts = null;
        try {
            posts = postService.findAllPosts().stream().filter(p -> p.getUser().getId() == (long) id)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< retrieveAllPostsForSpecificUser");
        return posts;
    }

    @PostMapping(value = "/posts")
    public ResponseEntity<Object> createPost(@RequestBody DTO dto) {
        LOG.info("> createPost");
        ResponseEntity<Object> responseEntity = null;
        try {
            Optional<User> user = userService.findOne(dto.id);
            Optional<Category> category = categoryService.findOne(dto.categoryId);
            if (user.isPresent() && category.isPresent()) {
                dto.post.setUser(user.get());
                dto.post.setCategory(category.get());
                Post savedPost = postService.save(dto.post);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                        .buildAndExpand(savedPost.getId()).toUri();
                responseEntity = ResponseEntity.created(location).build();
            } else
                responseEntity = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< createPost");
        return responseEntity;
    }

    @PutMapping(value = "/posts")
    public ResponseEntity<Object> updatePost(@RequestBody DTO dto) {
        LOG.info("> updatePost");
        ResponseEntity<Object> responseEntity = null;
        try {
            Optional<User> user = userService.findOne(dto.id);
            Optional<Category> category = categoryService.findOne(dto.categoryId);
            if (user.isPresent() && category.isPresent()) {
                dto.post.setUser(user.get());
                dto.post.setCategory(category.get());
                Post savedPost = postService.save(dto.post);
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}")
                        .buildAndExpand(savedPost.getId()).toUri();
                responseEntity = ResponseEntity.created(location).build();
            } else
                responseEntity = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< updatePost");
        return responseEntity;
    }

    @DeleteMapping(value = "/posts/{id}", produces = "application/json")
    public ResponseEntity<Object> deletePost(@PathVariable int id) {
        LOG.info("> deletePost");
        ResponseEntity<Object> responseEntity = null;
        try {
            Optional<Post> post = postService.findOne(id);
            if (!post.isPresent())
                throw new UserNotFoundException("id-" + id);
            else
                postService.delete(id);
            responseEntity = ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserNotFoundException e) {
            LOG.error("Post owner does not exist: {} {}", e, e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).build();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        LOG.info("< deletePost");
        return responseEntity;
    }
}

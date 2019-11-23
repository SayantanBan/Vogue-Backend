package tk.sayantan.Vogue.service;

import tk.sayantan.Vogue.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post findByTitle(String title);

    List<Post> findAllPosts();

    Post save(Post post);

    Optional<Post> findOne(int id);

    void delete(int id);
}

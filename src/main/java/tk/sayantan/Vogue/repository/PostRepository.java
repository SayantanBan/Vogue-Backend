package tk.sayantan.Vogue.repository;

import org.springframework.data.repository.CrudRepository;
import tk.sayantan.Vogue.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {
    Post findBypostName(String postName);
}

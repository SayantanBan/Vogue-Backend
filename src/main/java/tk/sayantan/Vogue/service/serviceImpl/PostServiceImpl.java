package tk.sayantan.Vogue.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.sayantan.Vogue.model.Post;
import tk.sayantan.Vogue.repository.PostRepository;
import tk.sayantan.Vogue.service.PostService;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Override
    public Post findByTitle(String title) {
        return postRepo.findBypostName(title);
    }

    @Override
    public List<Post> findAllPosts() {
        return (List<Post>) postRepo.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepo.save(post);
    }

    @Override
    public Optional<Post> findOne(int id) {
        return postRepo.findById((long) id);
    }

    @Override
    public void delete(int id) {
        postRepo.deleteById((long) id);
    }

}

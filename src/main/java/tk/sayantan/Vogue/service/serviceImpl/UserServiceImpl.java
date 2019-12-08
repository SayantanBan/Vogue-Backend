package tk.sayantan.Vogue.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.sayantan.Vogue.model.User;
import tk.sayantan.Vogue.repository.UserRepository;
import tk.sayantan.Vogue.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User findByEmail(String username) {
        return repository.findByEmail(username);
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findOne(int id) {
        return repository.findById((long) id);
    }

}

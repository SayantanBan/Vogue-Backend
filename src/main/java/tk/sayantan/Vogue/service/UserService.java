package tk.sayantan.Vogue.service;

import tk.sayantan.Vogue.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByEmail(String title);

    List<User> findAllUsers();

    User save(User user);

    Optional<User> findOne(int id);
}

package tk.sayantan.Vogue.repository;

import org.springframework.data.repository.CrudRepository;
import tk.sayantan.Vogue.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String username);
}


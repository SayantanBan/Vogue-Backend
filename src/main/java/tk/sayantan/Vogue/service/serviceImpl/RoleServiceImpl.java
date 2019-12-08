package tk.sayantan.Vogue.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.sayantan.Vogue.model.Role;
import tk.sayantan.Vogue.repository.RoleRepository;
import tk.sayantan.Vogue.service.RoleService;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository repository;

    @Override
    public Optional<Role> findOne(int id) {
        return repository.findById((long) id);
    }

}

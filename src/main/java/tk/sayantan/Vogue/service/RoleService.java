package tk.sayantan.Vogue.service;

import tk.sayantan.Vogue.model.Role;

import java.util.Optional;


public interface RoleService {
    Optional<Role> findOne(int id);
}

package tk.sayantan.Vogue.service.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tk.sayantan.Vogue.model.User;
import tk.sayantan.Vogue.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;


@Component
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LOG.info("> processAppUserDetails");
        User user = userRepository.findByEmail(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getEmail(), user.getPassword(), authorities);
        LOG.info("< processAppUserDetails");
        return userDetails;
    }
}

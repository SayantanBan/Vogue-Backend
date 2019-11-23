package tk.sayantan.Vogue.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tk.sayantan.Vogue.model.User;
import tk.sayantan.Vogue.security.SecurityUtility;
import tk.sayantan.Vogue.service.RoleService;
import tk.sayantan.Vogue.service.UserService;
import tk.sayantan.Vogue.utility.MailConstructor;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;

@RestController
@RequestMapping("/public")
public class UserResource {

    private static final Logger LOG = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    private UserService service;
    @Autowired
    private MailConstructor mailConstructor;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/users/newUser", method = RequestMethod.POST)
    @CrossOrigin(origins = {"http://localhost:4200", "https://vogue-blog.firebaseapp.com"})
    public ResponseEntity<Object> newUserPost(@RequestBody HashMap<String, String> mapper) {
        LOG.info("> processNewUserCreation");
        ResponseEntity<Object> responseEntity = null;
        try {
            String userEmail = mapper.get("username");
            String userFirstname = mapper.get("firstname");
            String userLastname = mapper.get("lastname");
            if (service.findByEmail(userEmail) != null) {
                responseEntity = ResponseEntity.status(HttpStatus.IM_USED).build();
                LOG.debug("User already exists");
            } else {
                User user = new User();
                user.setEmail(userEmail);
                user.setFirstName(userFirstname);
                user.setLastName(userLastname);
                if (roleService.findOne(2).isPresent())
                    user.setRoles(Arrays.asList(roleService.findOne(2).get()));
                String password = SecurityUtility.randomPassword();
                String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
                user.setPassword(encryptedPassword);
                SimpleMailMessage email = mailConstructor.constructNewUserEmail(user, password);
                mailSender.send(email);
                service.save(user);
                LOG.debug("User successfully created");
                URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(user.getId())
                        .toUri();
                responseEntity = ResponseEntity.created(location).build();
            }
        } catch (Exception ex) {
            LOG.error("Unexpected System Error: {} {}", ex, ex.getMessage());
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        LOG.info("> processNewUserCreation");
        return responseEntity;
    }

}

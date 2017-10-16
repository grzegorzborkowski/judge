package judge;

import judge.Entity.User;
import judge.Service.PasswordService;
import judge.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartUpInit {

    @Autowired
    UserService userService;
    @Autowired
    PasswordService passwordService;

    @PostConstruct
    public void init(){
        User user = new User();
        String encryptedPassword = passwordService.encrypt("admin");

        user.setUsername("admin");
        user.setRole("admin");
        user.setEmail("admin@mail.com");
        user.setPassword(encryptedPassword);
        user.setCourse("default");

        userService.addUser(user);
    }
}
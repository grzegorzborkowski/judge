package judge;

import judge.Entity.User;
import judge.Service.PasswordService;
import judge.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StartUpInit {

    @Autowired
    UserService userService;
    @Autowired
    PasswordService passwordService;

    @Value("${database.admin.username}")
    String adminUsername;
    @Value("${database.admin.password}")
    String adminDecryptedPassword;
    @Value("${database.admin.role}")
    String adminRole;
    @Value("${database.admin.email}")
    String adminEmail;
    @Value("${database.admin.course}")
    String adminCourse;

    @PostConstruct
    public void addAdminOnInit(){
        User user = new User();
        String encryptedPassword = passwordService.encrypt("admin");

        user.setUsername(adminUsername);
        user.setRole(adminRole);
        user.setEmail(adminEmail);
        user.setPassword(encryptedPassword);
        user.setCourse(adminCourse);

        userService.addUser(user);
    }
}

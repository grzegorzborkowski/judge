package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Entity.User;
import judge.Service.PasswordService;
import judge.Service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<User> getAllUsers() {
        logger.info("Processing GET /user/getAll");
        return this.userService.getAllUsers();
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public User getById(@RequestParam BigInteger id) {
        logger.info("Processing GET /user/getById");
        User user = this.userService.getUserById(id);
        return user;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody JsonNode userJson) {

        logger.info("Processing POST /user/add");

        User user = new User();
        String encryptedPassword = this.passwordService.encrypt(userJson.get("password").asText());

        user.setUsername(userJson.get("username").asText());
        user.setRole(userJson.get("role").asText());
        user.setEmail(userJson.get("email").asText());
        user.setPassword(encryptedPassword);
        user.setCourse("default");
        user.setId(new BigInteger(userJson.get("facebookId").asText()));

        logger.info("Add: " + userJson.get("username").asText());
        String status = this.userService.addUser(user);

        return status;
    }
}

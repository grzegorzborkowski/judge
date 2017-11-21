package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import judge.Entity.User;
import judge.Service.PasswordService;
import judge.Service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.Base64;
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

    /**
     *
     * @param userJson [username, password, role, email, id]
     */
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

        logger.info("Add: " + userJson.get("username").asText());
        String status = this.userService.addUser(user);

        return status;
    }

    /**
     *
     * @param studentsJson [usernames, password, course]
     */
    @RequestMapping(value = "/addMultipleStudents", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addMultipleStudents(@RequestBody JsonNode studentsJson) {

        logger.info("Processing POST /user/addMultipleStudents");

        int studentsCounter = 0;

        String usernamesAll = studentsJson.get("usernames").asText();
        String[] usernames = usernamesAll.split(",");
        String password = studentsJson.get("password").asText();
        String course = studentsJson.get("course").asText();

        for(String username : usernames) {
            studentsCounter += 1;

            User user = new User();
            String encryptedPassword = this.passwordService.encrypt(password);

            user.setUsername(username);
            user.setRole("student");
            user.setPassword(encryptedPassword);
            user.setCourse(course);

            logger.info("Add user: " + username);
            this.userService.addUser(user);
        }

        return "Added " + studentsCounter + " new student(s)";
    }

    /**
     *
     * @param teacherJson [username, password]
     */
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addTeacher(@RequestBody JsonNode teacherJson) {

        logger.info("Processing POST /user/addTeacher");

        String username = teacherJson.get("username").asText();
        String password = teacherJson.get("password").asText();

        User user = new User();
        String encryptedPassword = this.passwordService.encrypt(password);

        user.setUsername(username);
        user.setRole("teacher");
        user.setPassword(encryptedPassword);

        logger.info("Add user: " + username);
        String status = this.userService.addUser(user);

        return status;
    }


    /**
     *
     * @param userJson [password]
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String changePassword(@RequestBody JsonNode userJson, HttpServletResponse response) {

        logger.info("Processing POST /user/changePassword");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        logger.warn(username);

        String newPassword = userJson.get("password").asText();
        String encryptedPassword = this.passwordService.encrypt(newPassword);

        logger.info("Password changed for: " + username);
        this.userService.changePassword(username, encryptedPassword);

        String tokenBase = username + ":" + newPassword;
        String token = Base64.getEncoder().encodeToString((tokenBase).getBytes());

        response.setStatus(HttpServletResponse.SC_OK);
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("token", "Basic " + token);
        return result.toString();
    }

}

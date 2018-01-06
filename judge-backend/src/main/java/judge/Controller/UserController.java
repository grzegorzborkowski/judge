package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import judge.Entity.User;
import judge.Service.responses.AddUserStatus;
import judge.Service.PasswordService;
import judge.Service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static judge.Utils.RUNTIME_USERS_DIR_NAME;

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
     * @param studentsJson [username, firstName, lastName, password, course]
     */
    @RequestMapping(value = "/addOneStudent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addOneStudent(@RequestBody JsonNode studentsJson,
                             HttpServletResponse response) {

        logger.info("Processing POST /user/addOneStudent");

        String username = studentsJson.get("username").asText();
        String firstName = studentsJson.get("firstName").asText();
        String lastName = studentsJson.get("lastName").asText();
        String password = studentsJson.get("password").asText();
        String course = studentsJson.get("course").asText();

        User user = new User();
        String encryptedPassword = this.passwordService.encrypt(password);

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole("student");
        user.setPassword(encryptedPassword);
        user.setCourse(course);

        logger.info("Add user: " + username);


        AddUserStatus result = this.userService.addUser(user);

        return handleAddUserResponse(result, username, response);
    }


    @RequestMapping(value = "/addMultipleStudents", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public String addMultipleStudents(@RequestBody MultipartFile file,
                                      HttpServletResponse response) {

        logger.info("Processing POST /user/addMultipleStudents");

        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmm").format(Calendar.getInstance().getTime());
        String filename =  RUNTIME_USERS_DIR_NAME + "students-" + timestamp + ".csv";
        System.out.println(filename);
        Path localFilePath = Paths.get(filename);

        try {
            Files.write(localFilePath, file.getBytes());
        } catch (IOException e) {
            logger.warn("Error while storing file with students' details.");
            logger.warn(e);
        }

        AddUserStatus result = userService.addStudentsFromFile(filename);

        return handleAddMultipleStudentsResponse(result, response);
    }

    /**
     * @param teacherJson [username, password]
     */
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addTeacher(@RequestBody JsonNode teacherJson,
                             HttpServletResponse response) {

        logger.info("Processing POST /user/addTeacher");

        String username = teacherJson.get("username").asText();
        String firstName = teacherJson.get("firstName").asText();
        String lastName = teacherJson.get("lastName").asText();
        String password = teacherJson.get("password").asText();

        User user = new User();
        String encryptedPassword = this.passwordService.encrypt(password);

        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole("teacher");
        user.setPassword(encryptedPassword);

        logger.info("Add teacher: " + username);
        AddUserStatus result = this.userService.addUser(user);

        return handleAddUserResponse(result, username, response);

    }

    private String handleAddUserResponse(AddUserStatus result,
                                         String username,
                                         HttpServletResponse response) {
        switch (result) {
            case OK:
                response.setStatus(HttpServletResponse.SC_CREATED);
                return "A new user has been added";
            case USER_ALREADY_EXISTS:
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return "User " + username + " already exists";
            default:
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return "Unexpected AddUserStatus code";
        }
    }

    private String handleAddMultipleStudentsResponse(AddUserStatus result,
                                         HttpServletResponse response) {
        switch (result) {
            case OK:
                response.setStatus(HttpServletResponse.SC_CREATED);
                return "Students have been added";
            case USER_ALREADY_EXISTS:
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                return "Redundant usernames. Some of the users already exist in database. " +
                        "Students with unique usernames have been added";
            case FILE_PARSING_EXCEPTION:
                response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                return "Exception occurred while processing the file. Check file's format";
            default:
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return "Unexpected status code code";
        }
    }


    /**
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

    @RequestMapping(value = "/getInfo", method = RequestMethod.POST)
    public User getInfo(HttpServletResponse response) {

        logger.info("Processing POST /user/getInfo");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        logger.warn(username);

        return this.userService.getUserByUsername(username);
    }

}

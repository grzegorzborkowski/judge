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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@CrossOrigin
@RestController
public class LoginController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(LoginController.class);

    @Autowired
    PasswordService passwordService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestBody JsonNode userJson, HttpServletResponse response) {
        logger.info("Processing POST /login");
        String username;
        String password;

        try {
            username = userJson.get("username").asText();
            password = userJson.get("password").asText();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            logger.info("Parsing login request failed");
            return "Logging failed";
        }

        User user = userService.getUserByUsername(username);

        if (user != null) {
            if (this.passwordService.validate(password, user.getPassword())) {
                String tokenBase = username + ":" + password;
                String token = Base64.getEncoder().encodeToString((tokenBase).getBytes());

                logger.info("Logging for user " + username + " successful");
                response.setStatus(HttpServletResponse.SC_OK);
                ObjectNode result = JsonNodeFactory.instance.objectNode();
                result.put("token", "Basic " + token);
                result.put("role", user.getRole());
                return result.toString();
            } else {
                logger.info("Logging for user " + username + " failed. Wrong password provided");
            }
        } else {
            logger.info("Logging failed. Wrong username provided: " + username);
        }
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return "Logging failed";
    }
}

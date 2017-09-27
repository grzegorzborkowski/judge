package judge.Service;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Dao.UserDao;
import judge.Entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
public class UserService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers() {
        List<User> studentList = new ArrayList<>();
        Iterable<User> users = userDao.findAll();
        users.forEach(studentList::add);
        return studentList;
    }

    public User getUserById(BigInteger id) {
        User student = userDao.findById(id);
        return student;
    }

    public User getUserByUsername(String username) {
        User user = userDao.findByUsername(username);
        if(user!=null) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
            List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>();
            roles.add(simpleGrantedAuthority);
            user.setAuthorities(roles);
        }
        return user;
    }

    public String addUser(User student) {
        if(this.userDao.findById(student.getId()) == null) {
            this.userDao.save(student);
            return "New student has been added.";
        } else {
            logger.warn("User with the same ID already exists.");
            return "User with given ID already exists. Adding failed.";
        }
    }

    //TODO: Remove it...
    public String addUserEmergencyMode(JsonNode submissionJson) {
        User student = new User();
        student.setId(new BigInteger(submissionJson.get("facebookId").asText()));
        student.setUsername("Gall Anonim");

        String status = addUser(student);
        return status;
    }

    public UserService() {

    }
}

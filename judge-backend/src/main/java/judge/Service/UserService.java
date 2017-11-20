package judge.Service;

import judge.Dao.UserDao;
import judge.Entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.access.method.P;
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
        List<User> userList = new ArrayList<>();
        Iterable<User> users = userDao.findAll();
        users.forEach(userList::add);
        return userList;
    }

    public User getUserById(BigInteger id) {
        User user = userDao.findById(id);
        return user;
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

    public String addUser(User user) {
        if(this.userDao.findByUsername(user.getUsername()) == null) {
            this.userDao.save(user);
            logger.info("User " + user.getUsername() + " has been added.");
            return "User " + user.getUsername() + " has been added.";
        } else {
            logger.warn("User " + user.getUsername() + " already exists. Adding failed.");
            return "User " + user.getUsername() + " already exists. Adding failed.";
        }
    }

    public String changePassword(String username, String password) {
        User user = this.userDao.findByUsername(username);
        if(user != null) {
            user.setPassword(password);
            userDao.save(user);
            return "Password has been successfully updated";
        } else {
            return "Operation failed. System can't identify the current user";
        }
    }

    public UserService() {

    }
}

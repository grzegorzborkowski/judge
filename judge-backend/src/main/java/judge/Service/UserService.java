package judge.Service;

import judge.Dao.UserDao;
import judge.Entity.User;
import judge.Service.responses.AddUserStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@Configurable
public class UserService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private FileService fileService;

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

    public boolean userWithGivenUsernameExists(String username) {
        User user = userDao.findByUsername(username);
        return user != null;
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

    public AddUserStatus addUser(User user) {
        if(this.userDao.findByUsername(user.getUsername()) == null) {
            this.userDao.save(user);
            logger.info("User " + user.getUsername() + " has been added.");
            return AddUserStatus.OK;
        } else {
            logger.warn("User " + user.getUsername() + " already exists. Adding failed.");
            return AddUserStatus.USER_ALREADY_EXISTS;
        }
    }

    public AddUserStatus addStudentsFromFile(String filename) {
        AddUserStatus status = AddUserStatus.OK;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            String line;
            Scanner scanner;
            String data;

            while ((line = reader.readLine()) != null) {
                User user = new User();
                scanner = new Scanner(line);
                scanner.useDelimiter(",");
                while (scanner.hasNext()) {
                    data = scanner.next();
                        user.setUsername(data);
                    data = scanner.next();
                        user.setFirstName(data);
                    data = scanner.next();
                        user.setLastName(data);
                    data = scanner.next();
                        String encryptedPassword = this.passwordService.encrypt(data);
                        user.setPassword(encryptedPassword);
                }

                user.setRole("student");
                AddUserStatus currentStatus = addUser(user);

                if (currentStatus==AddUserStatus.USER_ALREADY_EXISTS) {
                    status = AddUserStatus.USER_ALREADY_EXISTS;
                }
            }
        } catch (Exception e) {
            status = AddUserStatus.FILE_PARSING_EXCEPTION;
            logger.warn(e);
        }

        fileService.removeFile(filename);

        return status;
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

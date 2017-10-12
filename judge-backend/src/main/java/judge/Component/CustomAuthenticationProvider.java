package judge.Component;

import judge.Entity.User;
import judge.Service.PasswordService;
import judge.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    PasswordService passwordService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        logger.info("User: " + username);
        User user = userService.getUserByUsername(username);

        if(user!=null) {
            if(this.passwordService.validate(password, user.getPassword())){
                logger.info("User authenticated");
                logger.info("Roles: ", user.getAuthorities());
                return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
            }
        }

        logger.info("Authentication failed");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}

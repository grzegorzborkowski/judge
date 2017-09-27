package judge.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(4);

    public PasswordService() {
    }

    public String encrypt(String password) {
        String encryptedPassword = passwordEncoder.encode(password);
        return encryptedPassword;
    }

    public boolean validate(String candidatePassword, String encodedPassword) {
        return passwordEncoder.matches(candidatePassword, encodedPassword);
    }
}

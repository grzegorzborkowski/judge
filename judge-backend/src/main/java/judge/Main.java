package judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;

import static judge.Utils.RUNTIME_CODE_DIR_NAME;
import static judge.Utils.RUNTIME_DIR_NAME;
import static judge.Utils.RUNTIME_USERS_DIR_NAME;

@SpringBootApplication
@EnableAsync

public class Main {

    public static void main(String[] args) {
        createRuntimeDir();
        SpringApplication.run(Main.class, args);
    }

    /*
        This is a temporary solution.
        Managing run time generated files will be changed after integration with sandbox environment.
     */
    public static void createRuntimeDir() {
        File runtimeDir = new File(RUNTIME_DIR_NAME);
        runtimeDir.mkdir();

        File runtimeCodeDir = new File(RUNTIME_CODE_DIR_NAME);
        runtimeCodeDir.mkdir();

        File runtimeUsersDir = new File(RUNTIME_USERS_DIR_NAME);
        runtimeUsersDir.mkdir();
    }
}

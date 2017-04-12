package judge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.File;

import static judge.Utils.RUNTIME_DIR_NAME;

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
    }
}

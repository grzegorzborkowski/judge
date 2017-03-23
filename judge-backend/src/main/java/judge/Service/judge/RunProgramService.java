package judge.Service.judge;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static judge.Utils.*;

@Service
class RunProgramService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(RunProgramService.class);

    static int runProgram(String name) {
        String output;
        try {
            Process p = Runtime.getRuntime().exec("./" + name);
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            logger.info("Here is the standard output of the program (if any):");
            while ((output = stdInput.readLine()) != null) {
                logger.info(output);
            }
            logger.info("Here is the standard error of the program (if any):");
            while ((output = stdError.readLine()) != null) {
                logger.info(output);
            }
        } catch (IOException e) {
            logger.error("Exception happened while running a program.", e);
            return RUN_FAILURE_CODE;
        }
        return RUN_SUCCESS_CODE;
    }
}

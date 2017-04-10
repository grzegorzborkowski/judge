package judge.Service.judge;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import static judge.Utils.*;

@Service
class CompileService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(CompileService.class);

    int compileSourceCode(String compilerName, String filename) {
        int compilationExitCode;
        String output;
        try {
            Process p = Runtime.getRuntime().exec(compilerName + " " + filename);
            p.waitFor();
            compilationExitCode = p.exitValue();
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            logger.debug("Here is the standard output of the compilation (if any):");
            while ((output = stdOut.readLine()) != null) {
                logger.debug(output);
            }
            logger.debug("Here is the standard error of the compilation (if any):");
            while ((output = stdError.readLine()) != null) {
                logger.debug(output);
            }
        } catch (Exception e) {
            logger.error("Exception happened during compilation.", e);
            return COMPILATION_FAILURE_CODE;
        }
        if (compilationExitCode == COMPILATION_SUCCESS_CODE) {
            logger.info("Compilation successful.");
            return COMPILATION_SUCCESS_CODE;
        }
        else {
            logger.warn("Compilation failed.");
            return COMPILATION_FAILURE_CODE;
        }
    }
}

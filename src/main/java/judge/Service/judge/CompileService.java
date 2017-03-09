package judge.Service.judge;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
class CompileService {
    static int compileSourceCode(String compilerName, String filename) {
            String s = null;
            int exitCode = 0;
            try {
                Process p = Runtime.getRuntime().exec(compilerName + " " + filename);
                p.waitFor();
                exitCode = p.exitValue();
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(p.getInputStream()));

                BufferedReader stdError = new BufferedReader(new
                        InputStreamReader(p.getErrorStream()));

                // read the output from the command
                System.out.println("Here is the standard output of the command:\n");
                while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
                }

                // read any errors from the attempted command
                System.out.println("Here is the standard error of the command (if any):\n");
                while ((s = stdError.readLine()) != null) {
                    System.out.println(s);
                }
            }
            catch (Exception e) {
                System.out.println("exception happened - here's what I know: ");
                e.printStackTrace();
                return -1;
            }
            if(exitCode==0) return 0;
            else return -1;
        }
}

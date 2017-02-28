package judge.Service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
public class JudgeService {

    public void compile(String code) throws IOException {
        sourceCodeFile(code);
        execProcess("gcc source_code.c");
        execProcess("./a.out");
    }

    private static void sourceCodeFile(String code) throws IOException {
        List<String> lines = Collections.singletonList(code);
        Path file = Paths.get("source_code.c");
        Files.write(file, lines, Charset.forName("UTF-8"));
    }

    private static void execProcess(String process) {
        String s = null;
        try {
            Process p = Runtime.getRuntime().exec(process);
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
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }
    }

}

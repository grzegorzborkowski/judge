package judge.Service.judge;

import judge.Entity.Problem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static judge.Utils.*;

@Service
class SourceCodeCreatorService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SourceCodeCreatorService.class);

    /**
     * Generates .c source code file.
     *
     * @param code  student's input (now: program, target: function)
     * @return path to the created source code file
     */
    String createSourceCodeFile(String code, Problem problem) {
        List<String> lines = prepareSourceCode(code, problem);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        /*
            Math.random() will be replaced by user ID.
            It is used to avoid overwriting generated source files during parallel tests.
         */
        String fileName = RUNTIME_CODE_DIR_NAME + "source_code" + timestamp + Math.random()  + ".c";
        System.out.println(fileName);
        Path file = Paths.get(fileName);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error("Exception happened while generating the source code file.", e);
        }
        return fileName;
    }

    private List<String> prepareSourceCode(String code, Problem problem) {
        List<String> result,generated_lines_1, generated_lines_2;
        result = new ArrayList<>();
        Path generated_file_1 = Paths.get(TEMPLATES_DIR_NAME + HEADER_C);
        Path generated_file_2 = Paths.get(TEMPLATES_DIR_NAME + MAIN_FUNCTION_C);

        try {
            generated_lines_1 = Files.readAllLines(generated_file_1);
            generated_lines_2 = Files.readAllLines(generated_file_2);

            result.addAll(generated_lines_1);
            result.addAll(Collections.singletonList(problem.getStructures()));
            result.addAll(Collections.singletonList(problem.getSolution()));
            result.addAll(Collections.singletonList(code));
            result.addAll(generated_lines_2);
        }
        catch (IOException e) {
            logger.error("Exception happened while generating the source code.", e);
        }
        return result;

    }
}

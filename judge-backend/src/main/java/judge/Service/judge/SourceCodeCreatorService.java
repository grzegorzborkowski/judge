package judge.Service.judge;

import judge.Service.ProblemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static judge.Utils.RUNTIME_DIR_NAME;

@Service
class SourceCodeCreatorService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SourceCodeCreatorService.class);

    @Autowired
    private ProblemService problemService;
    /**
     * Generates .c source code file.
     *
     *
     * @param problemID
     * @param code  student's input (now: program, target: function)
     * @return path to the created source code file
     */
    String createSourceCodeFile(Integer problemID, String code) {
        String template = problemService.getTemplateByProblemID(problemID);
        List<String> lines = new ArrayList<>();
        lines.add("#include <stdio.h> \n");
        lines.add(code);
        lines.add(template);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        /*
            Math.random() will be replaced by user ID.
            It is used to avoid overwriting generated source files during parallel tests.
         */
        String fileName = RUNTIME_DIR_NAME + "source_code" + timestamp + Math.random()  + ".c";
        System.out.println(fileName);
        Path file = Paths.get(fileName);
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error("Exception happened while generating the source code file.", e);
        }
        return fileName;
    }
}

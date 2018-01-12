package judge.Service.judge;

import judge.Entity.Problem;
import judge.Service.FileService;
import judge.Service.judge.structures.FileToExamine;
import judge.Service.judge.structures.SourceCodeFileType;
import judge.Service.judge.structures.SourceCodeStructure;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static judge.Utils.*;

@Service
class SourceCodeService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SourceCodeService.class);

    /**
     * Generates .c source code file.
     *
     * @param code  student's input (now: program, target: function)
     * @return path to the created source code file
     */
    FileToExamine createSourceCodeFile(String code, Problem problem, SourceCodeFileType sourceCodeFileType) {
        FileToExamine fileToExamine = new FileToExamine();

        SourceCodeStructure sourceCodeStructure = prepareSourceCode(code, problem, sourceCodeFileType);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        /*
            Math.random() will be replaced by user ID.
            It is used to avoid overwriting generated source files during parallel tests.
         */
        String fileName = RUNTIME_CODE_DIR_NAME + "source_code" + timestamp + Math.random()  + ".c";
        System.out.println(fileName);
        Path file = Paths.get(fileName);
        try {
            Files.write(file, sourceCodeStructure.getLines(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            logger.error("Exception happened while generating the source code file.", e);
        }

        fileToExamine.setFilename(fileName);
        fileToExamine.setLineWhereCustomCodeStarts(sourceCodeStructure.getLineWhereCustomCodeStarts());

        return fileToExamine;
    }

    private SourceCodeStructure prepareSourceCode(String code, Problem problem, SourceCodeFileType sourceCodeFileType) {
        SourceCodeStructure sourceCodeStructure = new SourceCodeStructure();

        List<String> result,generated_lines_1, generated_lines_2;
        result = new ArrayList<>();
        Path generated_file_1 = Paths.get(TEMPLATES_DIR_NAME + HEADER_C);
        Path generated_file_2 = Paths.get(TEMPLATES_DIR_NAME + MAIN_FUNCTION_C);

        int lineWhereCustomCodeStarts = 1;

        try {
            generated_lines_1 = Files.readAllLines(generated_file_1);
            generated_lines_2 = Files.readAllLines(generated_file_2);

            result.addAll(generated_lines_1);
            result.addAll(Collections.singletonList(problem.getStructures()));
            result.addAll(Collections.singletonList(problem.getSolution()));
            result.addAll(Collections.singletonList(code));
            result.addAll(generated_lines_2);

            /*
               For NEW_PROBLEM we need to count headers and structures only,
                as solution code is considered as custom code.
               For SUBMISSION we need to count solution code too,
                as code provided by a submitting person is considered as custom code.
             */
            lineWhereCustomCodeStarts += generated_lines_1.size();
            lineWhereCustomCodeStarts += problem.getStructures().split("\n").length;
            if(sourceCodeFileType==SourceCodeFileType.SUBMISSION) {
                lineWhereCustomCodeStarts += problem.getSolution().split("\n").length;
            }
        }
        catch (IOException e) {
            logger.error("Exception happened while generating the source code.", e);
        }

        sourceCodeStructure.setLines(result);
        sourceCodeStructure.setLineWhereCustomCodeStarts(lineWhereCustomCodeStarts);

        return sourceCodeStructure;
    }
}

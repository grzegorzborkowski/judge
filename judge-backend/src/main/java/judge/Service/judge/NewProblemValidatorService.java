package judge.Service.judge;

import judge.Component.JudgeResult;
import judge.Entity.Problem;
import judge.Service.FileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static judge.Utils.*;

@Service
@Configurable
public class NewProblemValidatorService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(NewProblemValidatorService.class);

    @Autowired
    AgentService agentService;
    @Autowired
    SourceCodeService sourceCodeService;
    @Autowired
    FileService fileService;

    /*
      Validates if a new problem is syntactically correct.
      Generates source code file based on the teacher's input and a signature of student's function.
      It uploads the new file to examine and verifies if compilation and rune codes are OK.
      All the defined tests are expected to fail while this execution.
     */
    public boolean validateNewProblem(Problem problem) {
        try {
            Path studentsSignature = Paths.get(TEMPLATES_DIR_NAME + STUDENTS_SIGNATURE_C);
            String sourceCodeFilename = sourceCodeService.createSourceCodeFile(String.join("",Files.readAllLines(studentsSignature)), problem);
            JudgeResult externalExaminationResult = agentService.uploadFileToExamine(sourceCodeFilename);
            fileService.removeFile(sourceCodeFilename);
            if(externalExaminationResult.getCompilationCode()==COMPILATION_SUCCESS_CODE
                    && externalExaminationResult.getRunCode()==RUN_SUCCESS_CODE) return true;
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return false;
    }
}

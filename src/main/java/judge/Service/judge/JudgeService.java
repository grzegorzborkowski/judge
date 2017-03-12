package judge.Service.judge;

import judge.Entity.Submission;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static judge.Utils.*;

@Service
public class JudgeService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(JudgeService.class);

    @Autowired
    SourceCodeCreatorService sourceCodeCreatorService;
    @Autowired
    CompileService compileService;
    @Autowired
    RunProgramService runProgramService;

    public Submission compileAndRun(String code) {
        Submission submission = new Submission();
        logger.info("Processing new submission.");
        submission.setCode(code);
        String filename = SourceCodeCreatorService.createSourceCodeFile(code);
        int compileResult = CompileService.compileSourceCode("gcc", filename);
        submission.setCompilationCode(compileResult);
        if (compileResult == COMPILATION_SUCCESS_CODE) {
            submission.setRunCode(this.runProgramService.runProgram("a.out"));
        } else {
            submission.setRunCode(RUN_FAILURE_CODE);
        }
        logger.info("Processing of the submission has finished.");
        return submission;
    }
}

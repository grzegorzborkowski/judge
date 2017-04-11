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
        String sourceCodeFilename = sourceCodeCreatorService.createSourceCodeFile(code);
        String executableFilename = sourceCodeFilename + ".out";
        String compilationParams = "-o " + executableFilename;
        int compileResult = compileService.compileSourceCode("gcc", compilationParams, sourceCodeFilename);
        submission.setCompilationCode(compileResult);
        if (compileResult == COMPILATION_SUCCESS_CODE) {
            submission.setRunCode(runProgramService.runProgram(executableFilename));
        } else {
            submission.setRunCode(RUN_FAILURE_CODE);
        }
        logger.info("Processing of the submission has finished.");
        return submission;
    }

    public void setCompileService(CompileService compileService) {
        this.compileService = compileService;
    }

    public void setSourceCodeCreatorService(SourceCodeCreatorService sourceCodeCreatorService) {
        this.sourceCodeCreatorService = sourceCodeCreatorService;
    }

    public void setRunProgramService(RunProgramService runProgramService) {
        this.runProgramService = runProgramService;
    }
}

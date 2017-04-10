package judge.Service.judge;

import judge.Entity.Submission;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static judge.TestUtils.HELLO_WORLD_SUBMISSION_CODE;
import static judge.Utils.COMPILATION_SUCCESS_CODE;
import static judge.Utils.RUN_SUCCESS_CODE;
import static org.mockito.Mockito.when;

public class JudgeServiceTest {
    private JudgeService judgeService = new JudgeService();
    private CompileService compileService = Mockito.mock(CompileService.class);
    private SourceCodeCreatorService sourceCodeCreatorService = Mockito.mock(SourceCodeCreatorService.class);
    private RunProgramService runProgramService = Mockito.mock(RunProgramService.class);
    private Submission expectedSubmission = new Submission();
    private String sourceCodeFile = "test/output/judge/Service/judge/source_code_01_ok.c";
    private String submissionData = HELLO_WORLD_SUBMISSION_CODE ;

    @Before
    public void init() {
        when(sourceCodeCreatorService.createSourceCodeFile(sourceCodeFile)).thenReturn(sourceCodeFile);
        when(compileService.compileSourceCode("gcc",sourceCodeFile)).thenReturn(COMPILATION_SUCCESS_CODE);
        when(runProgramService.runProgram("a.out")).thenReturn(RUN_SUCCESS_CODE);

        judgeService.setSourceCodeCreatorService(sourceCodeCreatorService);
        judgeService.setCompileService(compileService);
        judgeService.setRunProgramService(runProgramService);

        expectedSubmission.setCode(submissionData);
        expectedSubmission.setCompilationCode(COMPILATION_SUCCESS_CODE);
        expectedSubmission.setRunCode(RUN_SUCCESS_CODE);
    }
    @Test
    public void testCompileAndRun() {
        Submission actualSubmission = judgeService.compileAndRun(submissionData);
        Assert.assertEquals(actualSubmission.getCode(), expectedSubmission.getCode());
        Assert.assertEquals(actualSubmission.getCompilationCode(), expectedSubmission.getCompilationCode());
        Assert.assertEquals(actualSubmission.getRunCode(), expectedSubmission.getRunCode());
    }
}

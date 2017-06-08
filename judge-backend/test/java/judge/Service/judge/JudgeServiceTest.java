package judge.Service.judge;

import judge.Entity.Submission;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static judge.TestUtils.HELLO_WORLD_SUBMISSION_CODE;
import static judge.Utils.COMPILATION_SUCCESS_CODE;
import static judge.Utils.RUN_SUCCESS_CODE;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * TODO: add test for unreachable server case
 */
public class JudgeServiceTest {
    private JudgeService judgeService = new JudgeService();
    private SourceCodeCreatorService sourceCodeCreatorService = Mockito.mock(SourceCodeCreatorService.class);
    private AgentService agentService = Mockito.mock(AgentService.class);
    private Submission expectedSubmission = new Submission();
    private String sourceCodeFile = "test/output/judge/Service/judge/source_code_01_ok.c";
    private String submissionData = HELLO_WORLD_SUBMISSION_CODE ;
    private Map<String, Integer> expectedResult = new HashMap<>();

    // TODO: rewrite this as createSourceCodeFile receives now a problemID as a parameter
//    @Before
//    public void init() throws IOException {
//        when(sourceCodeCreatorService.createSourceCodeFile(sourceCodeFile)).thenReturn(sourceCodeFile);
//        expectedResult.put("compilationCode", COMPILATION_SUCCESS_CODE);
//        expectedResult.put("runCode", RUN_SUCCESS_CODE);
//        when(agentService.uploadFileToExamine(anyString())).thenReturn(expectedResult);
//        judgeService.setSourceCodeCreatorService(sourceCodeCreatorService);
//        judgeService.setAgentService(agentService);
//        expectedSubmission.setCode(submissionData);
//        expectedSubmission.setCompilationCode(COMPILATION_SUCCESS_CODE);
//        expectedSubmission.setRunCode(RUN_SUCCESS_CODE);
//    }
    //TODO: rewrite it to fit oauth and database changes
//    @Test
//    public void testCompileAndRun() {
//        Submission actualSubmission = judgeService.compileAndRun(submissionData, 1);
//        Assert.assertEquals(expectedSubmission.getCode(), actualSubmission.getCode());
//        Assert.assertEquals(expectedSubmission.getCompilationCode(), actualSubmission.getCompilationCode());
//        Assert.assertEquals(expectedSubmission.getRunCode(), actualSubmission.getRunCode());
//    }
}

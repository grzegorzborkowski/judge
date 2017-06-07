package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import judge.Component.ResultGenerator;
import judge.Entity.Submission;
import judge.Service.judge.JudgeService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static judge.TestUtils.COMPILATION_ERROR_SUBMISSION_CODE;
import static judge.TestUtils.HELLO_WORLD_SUBMISSION_CODE;
import static judge.TestUtils.TIMEOUT_SUBMISSION_CODE;
import static judge.Utils.*;
import static org.mockito.Mockito.when;

public class JudgeControllerTest {
    private JudgeController judgeController;
    private Submission submission;
    private ResultGenerator resultGenerator;
    private JudgeService judgeService;

    @Before
    public void init() {
        this.judgeController = new JudgeController();
        this.submission = Mockito.mock(Submission.class);
        this.resultGenerator = Mockito.mock(ResultGenerator.class);
        this.judgeService = Mockito.mock(JudgeService.class);
    }
    //TODO: rewrite it to fit oauth and database changes
//    @Test
//    public void testJudgeControllerSuccess() {
//        String submissionCode = HELLO_WORLD_SUBMISSION_CODE;
//        ObjectNode providedSubmission = prepareProvidedSubmission(submissionCode);
//        JsonNode expectedResult = prepareExpectedResult(COMPILATION_SUCCESS_CODE, RUN_SUCCESS_CODE);
//        prepareJudgeController(COMPILATION_SUCCESS_CODE, RUN_SUCCESS_CODE, submissionCode, expectedResult);
//        Assert.assertEquals(expectedResult.toString(), judgeController.insertSolution(providedSubmission));
//    }
//    @Test
//    public void testJudgeControllerFailure() {
//        String submissionCode = COMPILATION_ERROR_SUBMISSION_CODE;
//        ObjectNode providedSubmission = prepareProvidedSubmission(submissionCode);
//        JsonNode expectedResult = prepareExpectedResult(COMPILATION_FAILURE_CODE, RUN_FAILURE_CODE);
//        prepareJudgeController(COMPILATION_FAILURE_CODE, RUN_FAILURE_CODE, submissionCode, expectedResult);
//        Assert.assertEquals(expectedResult.toString(), judgeController.insertSolution(providedSubmission));
//    }
//    @Test
//    public void testJudgeControllerTimeout() {
//        String submissionCode = TIMEOUT_SUBMISSION_CODE;
//        ObjectNode providedSubmission = prepareProvidedSubmission(submissionCode);
//        JsonNode expectedResult = prepareExpectedResult(COMPILATION_SUCCESS_CODE, TIMEOUT_CODE);
//        prepareJudgeController(COMPILATION_SUCCESS_CODE, TIMEOUT_CODE, submissionCode, expectedResult);
//        Assert.assertEquals(expectedResult.toString(), judgeController.insertSolution(providedSubmission));
//    }
//
//    private JsonNode prepareExpectedResult(int compilationCode, int runCode) {
//        ObjectNode expectedResult = JsonNodeFactory.instance.objectNode();
//        expectedResult.put("compilationCode", compilationCode);
//        expectedResult.put("runCode", runCode);
//        return expectedResult;
//    }
//    private ObjectNode prepareProvidedSubmission(String submissionCode) {
//        ObjectNode providedSubmission = JsonNodeFactory.instance.objectNode();
//        providedSubmission.put("code", submissionCode);
//        return providedSubmission;
//    }
//    private void prepareJudgeController(int compilationCode, int runCode, String submissionCode, JsonNode expectedResult) {
//        submission.setCompilationCode(compilationCode);
//        submission.setRunCode(runCode);
//
//        when(judgeService.compileAndRun(submissionCode, 1)).thenReturn(submission);
//        when(resultGenerator.generateResult(submission)).thenReturn(expectedResult);
//
//        judgeController.setJudgeService(judgeService);
//        judgeController.setResultGenerator(resultGenerator);
//    }
}

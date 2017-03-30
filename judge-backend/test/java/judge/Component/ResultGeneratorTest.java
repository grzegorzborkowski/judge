package judge.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import judge.Entity.Submission;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static judge.Utils.*;
import static org.mockito.Mockito.when;

public class ResultGeneratorTest {
    private ResultGenerator resultGenerator;

    @Before
    public void init() {
        this.resultGenerator = new ResultGenerator();
    }
    @Test
    public void testGenerateFailedSubmissionResult() {
        ObjectNode expectedResult = JsonNodeFactory.instance.objectNode()
                .put("compilationCode", COMPILATION_FAILURE_CODE)
                .put("runCode", RUN_FAILURE_CODE);
        JsonNode actualResult = resultGenerator.generateFailedSubmissionResult();
        Assert.assertEquals(expectedResult, actualResult);
    }
    @Test
    public void testGenerateResult() {
        Submission submission = Mockito.mock(Submission.class);
        when(submission.getCompilationCode()).thenReturn(COMPILATION_SUCCESS_CODE);
        when(submission.getRunCode()).thenReturn(RUN_SUCCESS_CODE);

        ObjectNode expectedResult = JsonNodeFactory.instance.objectNode()
                .put("compilationCode", COMPILATION_SUCCESS_CODE)
                .put("runCode", RUN_SUCCESS_CODE);
        JsonNode actualResult = resultGenerator.generateResult(submission);
        Assert.assertEquals(expectedResult, actualResult);
    }
}

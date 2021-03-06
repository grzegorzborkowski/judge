package judge.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import judge.Entity.Submission;
import org.springframework.stereotype.Component;
import static judge.Utils.*;

@Component
public class ResultGenerator {

    public JsonNode generateResult(Submission submission){
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("compilationCode", submission.getCompilationCode());
        result.put("runCode", submission.getRunCode());
        result.put("testsPositive", submission.getTestsPositive());
        result.put("testsTotal", submission.getTestsTotal());
        result.put("timeTaken", submission.getTimeTaken());
        result.put("errorCode", submission.getErrorCode());
        return result;
    }

    public JsonNode generateFailedSubmissionResult() {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("compilationCode", COMPILATION_FAILURE_CODE);
        result.put("runCode", RUN_FAILURE_CODE);
        return result;
    }

    public JsonNode generateProcessingErrorSubmissionResult() {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("compilationCode", PROCESSING_ERROR_CODE);
        result.put("runCode", PROCESSING_ERROR_CODE);
        return result;
    }

    public JsonNode generateProblemValidationResult(String errorCode) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("errorCode", errorCode);
        if (errorCode.isEmpty()) result.put("message", "Problem has been saved.");
        else result.put("message", "Validation of the problem has failed. Please check its correctness.");
        return result;
    }
}

package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Component.ResultGenerator;
import judge.Service.judge.JudgeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/judge")
@CrossOrigin
class JudgeController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(JudgeService.class);

    @Autowired
    private JudgeService judgeService;
    @Autowired
    private ResultGenerator resultGenerator;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public String insertSolution(@RequestBody JsonNode submission) {
        String code;
        try {
            code = submission.get("code").asText();
        } catch (Exception e) {
            logger.error("Exception happened while parsing user input. Cannot extract code to process.", e);
            JsonNode result = this.resultGenerator.generateFailedSubmissionResult();
            return result.toString();
        }
        JsonNode result = this.resultGenerator.generateResult(this.judgeService.compileAndRun(code));
        return result.toString();
    }

    public void setJudgeService(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    public void setResultGenerator(ResultGenerator resultGenerator) {
        this.resultGenerator = resultGenerator;
    }
}

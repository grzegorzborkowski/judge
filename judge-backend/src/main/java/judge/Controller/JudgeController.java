package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Component.ResultGenerator;
import judge.Component.TokenValidator;
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
    @Autowired
    private TokenValidator tokenValidator;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public String insertSolution(@RequestBody JsonNode submission) {
        logger.info("New submission received.");
        String code;
        String token;
        String facebookID;
        try {
            code = submission.get("code").asText();
        } catch (Exception e) {
            logger.error("Exception happened while parsing user submission. Cannot extract code to process.", e);
            JsonNode result = this.resultGenerator.generateFailedSubmissionResult();
            return result.toString();
        }
        try {
            token = submission.get("token").asText();
            facebookID = submission.get("facebookID").asText();
        } catch (Exception e) {
            logger.error("Cannot extract access token and/or facebook user ID from the submission request.", e);
            // TODO: replace failedSubmissionResult with no submission processing
            JsonNode result = this.resultGenerator.generateFailedSubmissionResult();
            return result.toString();
        }
        Boolean isTokenValid = this.tokenValidator.validateToken(token, facebookID);
        if(isTokenValid) {
            JsonNode result = this.resultGenerator.generateResult(this.judgeService.compileAndRun(code));
            return result.toString();
        } else {
            //TODO: handle access denied, return special run/compilation code?
            return "ACCESS DENIED";
        }
    }

    public void setJudgeService(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    public void setResultGenerator(ResultGenerator resultGenerator) {
        this.resultGenerator = resultGenerator;
    }
}

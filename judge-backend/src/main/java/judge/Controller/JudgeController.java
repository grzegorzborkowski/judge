package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Component.ResultGenerator;
import judge.Component.TokenValidator;
import judge.Entity.Student;
import judge.Service.StudentService;
import judge.Service.judge.JudgeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/judge")
@CrossOrigin
class JudgeController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(JudgeService.class);

    @Autowired
    private JudgeService judgeService;
    //TODO: refactor, this is ugly
    @Autowired
    private StudentService studentService;
    @Autowired
    private ResultGenerator resultGenerator;
    @Autowired
    private TokenValidator tokenValidator;

    @RequestMapping(value = "/submit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public String insertSolution(@RequestBody JsonNode submission) {
        logger.info("Processing POST /judge");
        logger.info("New submission received.");
        String code;
        String token;
        BigInteger facebookID;
        Integer problemID = submission.get("problemID").asInt();
        try {
            code = submission.get("code").asText();
        } catch (Exception e) {
            logger.error("Exception happened while parsing user submission. Cannot extract code to process.", e);
            JsonNode result = this.resultGenerator.generateFailedSubmissionResult();
            return result.toString();
        }
        try {
            token = submission.get("token").asText();
            facebookID = new BigInteger(submission.get("facebookID").asText());
        } catch (Exception e) {
            logger.error("Cannot extract access token and/or facebook user ID from the submission request.", e);
            // TODO: replace failedSubmissionResult with no submission processing
            JsonNode result = this.resultGenerator.generateProcessingErrorSubmissionResult();
            return result.toString();
        }
        Boolean isTokenValid = this.tokenValidator.validateToken(token, facebookID.toString());
        //For testing
        //Boolean isTokenValid = true;
        Student author = this.studentService.getStudentById(facebookID);
        if(isTokenValid) {
            if (author != null) {
                logger.info("Found author: " + author.getUsername());
                JsonNode result = this.resultGenerator.generateResult(this.judgeService.compileAndRun(code, author, problemID));
                return result.toString();
            } else {
                //it should never happen
                logger.warn("Unexpected situation: submission of unregistered user. System will not process this submission.");
                this.studentService.addStudentEmergencyMode(submission);
                JsonNode result = this.resultGenerator.generateProcessingErrorSubmissionResult();
                return result.toString();            }
        } else {
            //TODO: handle access denied, return special run/compilation code?
            JsonNode result = this.resultGenerator.generateProcessingErrorSubmissionResult();
            return result.toString();
        }
    }

    public void setJudgeService(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    public void setResultGenerator(ResultGenerator resultGenerator) {
        this.resultGenerator = resultGenerator;
    }
}

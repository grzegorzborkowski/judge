package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Component.ResultGenerator;
import judge.Component.TokenValidator;
import judge.Entity.User;
import judge.Service.UserService;
import judge.Service.judge.JudgeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/judge")
@CrossOrigin
class JudgeController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(JudgeService.class);

    @Autowired
    private JudgeService judgeService;
    //TODO: refactor, this is ugly
    @Autowired
    private UserService userService;
    @Autowired
    private ResultGenerator resultGenerator;
    @Autowired
    private TokenValidator tokenValidator;

    /**
     *
     * @param submission [problemId, code, token, facebookId]
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public String insertSolution(@RequestBody JsonNode submission) {
        logger.info("Processing POST /judge");
        logger.info("New submission received.");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userService.getUserByUsername(username);

        String code;
        Integer problemID = submission.get("problemID").asInt();
        try {
            code = submission.get("code").asText();
        } catch (Exception e) {
            logger.error("Exception happened while parsing user submission. Cannot extract code to process.", e);
            JsonNode result = this.resultGenerator.generateFailedSubmissionResult();
            return result.toString();
        }

        logger.info("Identified author: " + author.getUsername());
        JsonNode result = this.resultGenerator.generateResult(this.judgeService.compileAndRun(code, author, problemID));
        return result.toString();
    }

    public void setJudgeService(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    public void setResultGenerator(ResultGenerator resultGenerator) {
        this.resultGenerator = resultGenerator;
    }
}

package judge.Controller;

import judge.Entity.Submission;
import judge.Service.SubmissionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;

@RestController
@RequestMapping("/submission")
@CrossOrigin
public class SubmissionController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SubmissionService.class);

    @Autowired
    private SubmissionService submissionService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<Submission> getAllSubmissions() {
        logger.info("Processing GET /submission/getAll");
        return this.submissionService.getAllSubmissions();
    }

    @RequestMapping(value = "/getAllForUser", method = RequestMethod.GET)
    public Collection<Submission> getSubmissionsByStudentId(@RequestParam BigInteger id) {
        logger.info("Processing GET /submission/getAllForUser");
        return this.submissionService.getSubmissionsByStudentId(id);
    }
}

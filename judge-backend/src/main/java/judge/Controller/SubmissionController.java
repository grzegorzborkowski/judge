package judge.Controller;

import judge.Entity.Submission;
import judge.Entity.User;
import judge.Service.SubmissionService;
import judge.Service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/submission")
@CrossOrigin
public class SubmissionController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SubmissionService.class);

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<Submission> getAllSubmissions() {
        logger.info("Processing GET /submission/getAll");
        List<Submission> submissionList = this.submissionService.getAllSubmissions();
        submissionList.sort(new SubmissionComparator());
        return submissionList;
    }

    @RequestMapping(value = "/getAllForUser", method = RequestMethod.GET)
    public Collection<Submission> getSubmissionsOfAuthenticatedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        BigInteger userId = user.getId();

        logger.info("Processing GET /submission/getAllForUser");
        return this.submissionService.getSubmissionsByStudentId(userId);
    }

    @RequestMapping(value = "/getAllForProblem", method = RequestMethod.GET)
    public Collection<Submission> getSubmissionsByProblemId(@RequestParam Integer id) {
        logger.info("Processing GET /submission/getAllForProblem");
        List<Submission> submissionList = this.submissionService.getSubmissionsByProblemId(id);
        submissionList.sort(new SubmissionComparator());
        return submissionList;
    }
}

class SubmissionComparator implements Comparator<Submission> {

    public int compare(Submission o1, Submission o2) {
        return percentage(o2) - percentage(o1);
    }

    public int percentage(Submission o)
    {
        if(o.getTestsTotal()!=0)
            return o.getTestsPositive() * 100 / o.getTestsTotal();
        return 0;
    }
}

package judge.Controller;

import judge.Entity.Problem;
import judge.Service.ProblemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/problems")
public class ProblemController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(StudentController.class);

    @Autowired
    private ProblemService problemService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<Problem> getAllProblems() {
        logger.info("Processing getAllProblems request");
        return this.problemService.getAllProblems();
    }

    @RequestMapping(value = "/getByID", method = RequestMethod.GET)
    public Problem getById(@RequestParam Integer id) {
        logger.info("Processing GET /problems/getByID");
        Problem problem = this.problemService.getProblemById(id);
        return problem;
    }
}

package judge.Service;

import judge.Component.ResultGenerator;
import judge.Dao.ProblemDao;
import judge.Entity.Problem;
import judge.Entity.Submission;
import judge.Service.judge.NewProblemValidatorService;
import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Configurable
public class ProblemService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(ProblemService.class);

    @Autowired
    private ProblemDao problemDao;
    @Autowired
    private NewProblemValidatorService problemValidatorService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private ResultGenerator resultGenerator;

    public Collection<Problem> getAllProblems() {
        List<Problem> problemList = new ArrayList<>();
        Iterable<Problem> problems = problemDao.findAll();
        problems.forEach(problemList::add);
        return problemList;
    }

    public Problem getProblemById(Integer id) {
        Problem problem = this.problemDao.findById(id);
        return problem;
    }

    public List<Problem> getProblemsByCategoryName(String name) {
        List<Problem> problems = this.problemDao.findByCategoryName(name);
        return problems;
    }

    public List<Problem> getProblemsByCategoryId(Integer id) {
        List<Problem> problems = this.problemDao.findByCategoryId(id);
        return problems;
    }

    public String addProblem(Problem problem) {
        if(this.problemDao.findByTitle(problem.getTitle()) == null) {
            if(problem.getCategory() == null) {
                return "Please specify a valid category";
            }

            String validationErrorMessage = this.problemValidatorService.validateNewProblem(problem);

            if(validationErrorMessage.isEmpty()) {
                this.problemDao.save(problem);
                return resultGenerator.generateProblemValidationResult(validationErrorMessage).toString();
            } else {
                return resultGenerator.generateProblemValidationResult(validationErrorMessage).toString();
            }
        } else {
            logger.warn("Problem with the same title already exists.");
            return resultGenerator.generateProblemValidationResult(
                    "Problem with given title already exists. Adding failed.").toString();
        }
    }

    public String saveProblem(Problem problem) {
        String validationErrorMessage = this.problemValidatorService.validateNewProblem(problem);

        if(validationErrorMessage.isEmpty()) {
            this.problemDao.save(problem);
        }

        return resultGenerator.generateProblemValidationResult(validationErrorMessage).toString();
    }

    @Transactional
    public String removeProblem(Integer id) {
        if(this.problemDao.findById(id) != null) {
            this.submissionService.removeSubmissionsForProblem(id);
            this.problemDao.removeById(id);
            return "Problem and all its corresponding submissions have been removed";
        } else {
            return "Problem with the given id doesn't exist";
        }
    }
}

package judge.Service;

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

    public List<Problem> getProblemsByCategory(String name) {
        List<Problem> problems = this.problemDao.findByCategoryName(name);
        return problems;
    }

    public String addProblem(Problem problem) {
        if(this.problemDao.findByTitle(problem.getTitle()) == null) {
            if(this.problemValidatorService.validateNewProblem(problem)) {
                this.problemDao.save(problem);
                return "New problem has been added.";
            } else {
                return "Problem invalid! Please double check your input and try again.";
            }
        } else {
            logger.warn("Problem with the same title already exists.");
            return "Problem with given title already exists. Adding failed.";
        }
    }

    public String saveProblem(Problem problem) {
        if(this.problemValidatorService.validateNewProblem(problem)) {
            this.problemDao.save(problem);
            return "Problem has been edited.";
        } else {
            return "Problem invalid! Please double check your input and try again.";
        }
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

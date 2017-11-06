package judge.Service;

import judge.Dao.ProblemDao;
import judge.Entity.Problem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Configurable
public class ProblemService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(ProblemService.class);

    @Autowired
    private
    ProblemDao problemDao;

    public Collection<Problem> getAllProblems() {
        List<Problem> problemList = new ArrayList<>();
        Iterable<Problem> students = problemDao.findAll();
        students.forEach(problemList::add);
        return problemList;
    }

    public Problem getProblemById(Integer id) {
        Problem problem = this.problemDao.findById(id);
        return problem;
    }


    public String addProblem(Problem problem) {
        if(this.problemDao.findById(problem.getId()) == null) {
            this.problemDao.save(problem);
            return "New problem has been added.";
        } else {
            logger.warn("Problem with the same ID already exists.");
            return "Problem with given ID already exists. Adding failed.";
        }
    }

    public String saveProblem(Problem problem) {
        this.problemDao.save(problem);
        return "Problem has been edited.";
    }
}

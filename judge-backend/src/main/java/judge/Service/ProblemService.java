package judge.Service;

import judge.Dao.ProblemDao;
import judge.Entity.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Configurable
public class ProblemService {
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
}

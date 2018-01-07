package judge.Service;

import judge.Dao.SubmissionDao;
import judge.Entity.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Configurable
public class SubmissionService {
    @Autowired
    SubmissionDao submissionDao;

    public List<Submission> getAllSubmissions() {
        List<Submission> submissionList = new ArrayList<>();
        Iterable<Submission> submissions = submissionDao.findAllByOrderByIdDesc();
        submissions.forEach(submissionList::add);
        return submissionList;
    }

    public List<Submission> getSubmissionsByStudentId(BigInteger id) {
        List<Submission> submissionList = new ArrayList<>();
        Iterable<Submission> submissions = submissionDao.findByAuthorIdOrderByIdDesc(id);
        submissions.forEach(submissionList::add);
        return submissionList;
    }

    public List<Submission> getSubmissionsByProblemId(Integer id) {
        List<Submission> submissionList = new ArrayList<>();
        Iterable<Submission> submissions = submissionDao.findByProblemIdOrderByIdDesc(id);
        submissions.forEach(submissionList::add);
        return submissionList;
    }

    public Submission getSubmissionById(Integer id) {
        Submission submission = submissionDao.findById(id);
        return submission;
    }

    public List<Submission> getSubmissionByProblemIdAndUserId(Integer problemId, BigInteger userId) {
        List<Submission> submissions = submissionDao.findByProblemIdAndAuthorIdOrderByIdDesc(problemId, userId);
        return submissions;
    }

    public void removeSubmissionsForProblem(Integer problemId) {
        submissionDao.removeAllByProblemId(problemId);
    }
}

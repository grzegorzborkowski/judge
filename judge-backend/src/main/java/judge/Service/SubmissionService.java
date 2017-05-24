package judge.Service;

import judge.Dao.SubmissionDao;
import judge.Entity.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
public class SubmissionService {
    @Autowired
    SubmissionDao submissionDao;

    public List<Submission> getAllSubmissions() {
        List<Submission> submissionList = new ArrayList<>();
        Iterable<Submission> students = submissionDao.findAll();
        students.forEach(submissionList::add);
        return submissionList;
    }

    public List<Submission> getSubmissionsByStudentId(BigInteger id) {
        List<Submission> submissionList = new ArrayList<>();
        Iterable<Submission> submissions = submissionDao.findByAuthorId(id);
        submissions.forEach(submissionList::add);
        return submissionList;
    }
}

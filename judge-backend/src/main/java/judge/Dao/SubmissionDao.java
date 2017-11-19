package judge.Dao;

import judge.Entity.Submission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface SubmissionDao extends CrudRepository<Submission, Integer> {
    List<Submission> findByAuthorId(BigInteger id);

    List<Submission> findByProblemId(Integer id);

    Submission findById(Integer id);

    void removeAllByProblemId(Integer id);
}

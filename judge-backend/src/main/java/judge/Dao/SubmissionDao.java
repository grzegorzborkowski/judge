package judge.Dao;

import judge.Entity.Submission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface SubmissionDao extends CrudRepository<Submission, Integer> {
    List<Submission> findByAuthorIdOrderByIdDesc(BigInteger id);

    List<Submission> findByProblemIdOrderByIdDesc(Integer id);

    List<Submission> findAllByOrderByIdDesc();

    Submission findById(Integer id);

    void removeAllByProblemId(Integer id);

    List<Submission> findByProblemIdAndAuthorIdOrderByIdDesc(Integer problemId, BigInteger authorId);
}

package judge.Dao;

import judge.Entity.Problem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemDao extends CrudRepository<Problem, Integer> {
    Problem findById(Integer id);
    Problem findByTitle(String title);
}

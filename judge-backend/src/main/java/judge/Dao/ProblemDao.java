package judge.Dao;

import judge.Entity.Problem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemDao extends CrudRepository<Problem, Integer> {
    Problem findById(Integer id);
    Problem findByTitle(String title);
    List<Problem> findByCategoryName(String name);
    List<Problem> findByCategoryId(Integer id);
    void removeById(Integer id);
}

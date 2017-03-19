package judge.Dao;

import judge.Entity.Student;
import org.springframework.data.repository.CrudRepository;


public interface StudentDao extends CrudRepository<Student, Integer> {
}

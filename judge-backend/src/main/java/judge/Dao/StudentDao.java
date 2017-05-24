package judge.Dao;

import judge.Entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface StudentDao extends CrudRepository<Student, BigInteger> {
    Student findById(BigInteger id);
}

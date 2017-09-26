package judge.Dao;

import judge.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface UserDao extends CrudRepository<User, BigInteger> {
    User findById(BigInteger id);
    User findByUsername(String username);
}

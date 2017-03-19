package judge.Service;

import judge.Dao.StudentDao;
import judge.Entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        this.studentDao.findAll().forEach(studentList::add);
        return studentList;
    }

    public void insertStudent(Student student) {
        this.studentDao.save(student);
    }
}

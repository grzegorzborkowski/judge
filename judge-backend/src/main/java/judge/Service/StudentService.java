package judge.Service;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Dao.StudentDao;
import judge.Entity.Student;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
public class StudentService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(StudentService.class);

    @Autowired
    private StudentDao studentDao;

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        Iterable<Student> students = studentDao.findAll();
        students.forEach(studentList::add);
        return studentList;
    }

    public Student getStudentById(BigInteger id) {
        Student student = studentDao.findById(id);
        return student;
    }

    public String addStudent(Student student) {
        if(this.studentDao.findById(student.getId()) == null) {
            this.studentDao.save(student);
            return "New student has been added.";
        } else {
            logger.warn("User with the same ID already exists.");
            return "User with given ID already exists. Adding failed.";
        }
    }

    //TODO: Remove it...
    public String addStudentEmergencyMode(JsonNode submissionJson) {
        Student student = new Student();
        student.setId(new BigInteger(submissionJson.get("facebookID").asText()));
        student.setUsername("Gall Anonim");

        String status = addStudent(student);
        return status;
    }

    public StudentService() {

    }
}

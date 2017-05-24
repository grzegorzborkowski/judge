package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Entity.Student;
import judge.Service.StudentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<Student> getAllStudents() {
        logger.info("Processing GET /student/getAll");
        return this.studentService.getAllStudents();
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public Student getById(@RequestParam BigInteger id) {
        logger.info("Processing GET /student/getById");
        Student student = this.studentService.getStudentById(id);
        return student;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addStudent(@RequestBody JsonNode studentJson) {
        logger.info("Processing POST /student/add");
        Student student = new Student();
        student.setUsername(studentJson.get("username").asText());
        student.setEmail(studentJson.get("email").asText());
        //student.setPassword("init");
        student.setCourse("default");
        student.setId(new BigInteger(studentJson.get("facebookID").asText()));
        logger.info("ID: " + studentJson.get("facebookID").asText());
        String status = this.studentService.addStudent(student);
        return status;
    }

}

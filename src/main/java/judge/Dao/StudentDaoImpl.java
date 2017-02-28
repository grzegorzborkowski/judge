package judge.Dao;

import judge.Entity.Student;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentDaoImpl implements StudentDao {

    private static Map<Integer, Student> students;

    /**
     * fake data
     */
    static {
        students = new HashMap<Integer, Student>() {
            {
                put(1, new Student(1, "Jacek", "CS"));
                put(2, new Student(2, "Zdzisiek", "Physics"));
            }
        };
    }

    @Override
    public Collection<Student> getAllStudents() {
        return students.values();
    }

    @Override
    public Student getStudentById(int id) {
        return students.get(id);
    }

    @Override
    public void deleteStudentById(int id) {
        students.remove(id);
    }

    @Override
    public void updateStudent(Student student) {
        Student s = students.get(student.getId());
        s.setCourse(student.getCourse());
        s.setName(student.getName());
        students.put(student.getId(), student);
    }

    @Override
    public void insertStudent(Student student) {
        this.students.put(student.getId(), student);
    }
}

package judge.Entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student extends User {

    private Set<Submission> submissions = new HashSet<>();
    private String course;


    @OneToMany(mappedBy = "author")
    public Set<Submission> getSubmissions()
    {
        return submissions;
    }

    public String getCourse()
    {
        return course;
    }

    public void setSubmissions(Set<Submission> submissions)
    {
        this.submissions = submissions;
    }

    public void setCourse(String course)
    {
        this.course = course;
    }
}

package judge.Entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Student extends User {

    private String course;
    @OneToMany(mappedBy = "author")
    private Set<Submission> submissions = new HashSet<>();

    public String getCourse()
    {
        return course;
    }

    public void setCourse(String course)
    {
        this.course = course;
    }

    public Set<Submission> getSubmissions()
    {
        return submissions;
    }

    public void setSubmissions(Set<Submission> submissions)
    {
        this.submissions = submissions;
    }
}

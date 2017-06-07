package judge.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends User
{

    private Set<Problem> problems = new HashSet<>();


    @OneToMany(mappedBy = "author")
    @JsonManagedReference
    public Set<Problem> getProblems()
    {
        return problems;
    }

    public void setProblems(Set<Problem> problems)
    {
        this.problems = problems;
    }
}

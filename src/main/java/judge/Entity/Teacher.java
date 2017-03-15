package judge.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Teacher extends User
{
    @OneToMany(mappedBy = "author")
    private Set<Problem> problems = new HashSet<>();

    public Set<Problem> getProblems()
    {
        return problems;
    }

    public void setProblems(Set<Problem> problems)
    {
        this.problems = problems;
    }
}

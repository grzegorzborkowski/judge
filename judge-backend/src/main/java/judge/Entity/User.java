package judge.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.*;

//
@Entity
@Table(name = "user_general")
public class User
{
    // student specific

    private Set<Submission> submissions = new HashSet<>();
    private String course;


    @OneToMany(mappedBy = "author")
    @JsonManagedReference
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


    // teacher specific


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


    private BigInteger id;
    private String username;
    private String role;
    private String password;
    private String email;

    public User()
    {
    }

    @Id
    public BigInteger getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail()
    {
        return email;
    }

    public void setId(BigInteger id)
    {
        this.id = id;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}

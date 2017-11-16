package judge.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import judge.Component.JudgeResult;

import javax.persistence.*;

@Entity
@Table(name = "submission")
public class Submission {

    private Integer id;
    //To break infinite recursion with JSON and JPA (we have bi-directional relationships now)
    //TODO: do in in a better way, delete bi-directional relationships
    @JsonIgnore
    private User author;
    private String code;
    private Integer compilationCode;
    private Integer runCode;
    private Integer testsTotal;
    private Integer testsPositive;
    private Float timeTaken;
    private Problem problem;
    private String fullCode;

    public Submission(User author, String code, Integer compilationCode, Integer runCode, Integer testsTotal, Integer testsPositive, Float timeTaken, Problem problem) {
        this.author = author;
        this.code = code;
        this.compilationCode = compilationCode;
        this.runCode = runCode;
        this.testsTotal = testsTotal;
        this.testsPositive = testsPositive;
        this.timeTaken = timeTaken;
        this.problem = problem;
    }

    public Submission() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    @ManyToOne
    @JsonBackReference
    public User getAuthor() {
        return author;
    }

    @Column(length = 2048)
    public String getCode() {
        return code;
    }

    public Integer getCompilationCode() {
        return compilationCode;
    }

    public Integer getRunCode() {
        return runCode;
    }


    public Integer getTestsTotal() {
        return testsTotal;
    }

    public Integer getTestsPositive() {
        return testsPositive;
    }

    public Float getTimeTaken() {
        return timeTaken;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCompilationCode(Integer compilationCode) {
        this.compilationCode = compilationCode;
    }

    public void setRunCode(Integer runCode) {
        this.runCode = runCode;
    }

    public void setTestsTotal(Integer testsTotal) {
        this.testsTotal = testsTotal;
    }

    public void setTestsPositive(Integer testsPositive) {
        this.testsPositive = testsPositive;
    }

    public void setTimeTaken(Float timeTaken) {
        this.timeTaken = timeTaken;
    }

    @ManyToOne
    @JoinColumn(name = "ProblemID")
    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String getFullCode() {
        return fullCode;
    }

    public void setFullCode(String fullCode) {
        this.fullCode = fullCode;
    }

    public void fillWithResult(JudgeResult result) {
        this.setCompilationCode(result.getCompilationCode());
        this.setRunCode(result.getRunCode());
        this.setTestsPositive(result.getTestsPositive());
        this.setTestsTotal(result.getTestsTotal());
        this.setTimeTaken(result.getTimeTaken());
    }
}

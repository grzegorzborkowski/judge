package judge.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "submission")
public class Submission {

    private Integer id;
    //To break infinite recursion with JSON and JPA (we have bi-directional relationships now)
    //TODO: do in in a better way, delete bi-directional relationships
    @JsonIgnore
    private Student author;
    private String code;
    private Integer compilationCode;
    private Integer runCode;
    private Integer testsTotal;
    private Integer testsPositive;
    private Problem problem;
    private String fullCode;

    public Submission(Student author, String code, Integer compilationCode, Integer runCode, Integer testsTotal, Integer testsPositive) {
        this.author = author;
        this.code = code;
        this.compilationCode = compilationCode;
        this.runCode = runCode;
        this.testsTotal = testsTotal;
        this.testsPositive = testsPositive;
    }

    public Submission(Student author, String code, Integer compilationCode, Integer runCode) {
        this.author = author;
        this.code = code;
        this.compilationCode = compilationCode;
        this.runCode = runCode;
    }

    public Submission(String code, Integer compilationCode, Integer runCode) {
        this.code = code;
        this.compilationCode = compilationCode;
        this.runCode = runCode;
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
    public Student getAuthor() {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAuthor(Student author) {
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

    @OneToOne(cascade = CascadeType.ALL)
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
}

package judge.Entity;

import javax.persistence.*;

@Entity
public class Submission
{

    private Integer id;
    private Student author;
    private String code;
    private Integer compilationCode;
    private Integer runCode;

    public Submission(Student author, String code, Integer compilationCode, Integer runCode)
    {
        this.author = author;
        this.code = code;
        this.compilationCode = compilationCode;
        this.runCode = runCode;
    }

    public Submission(String code, Integer compilationCode, Integer runCode)
    {
        this.code = code;
        this.compilationCode = compilationCode;
        this.runCode = runCode;
    }

    public Submission()
    {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId()
    {
        return id;
    }

    @ManyToOne
    public Student getAuthor()
    {
        return author;
    }

    public String getCode()
    {
        return code;
    }

    public Integer getCompilationCode()
    {
        return compilationCode;
    }

    public Integer getRunCode()
    {
        return runCode;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public void setAuthor(Student author)
    {
        this.author = author;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setCompilationCode(Integer compilationCode)
    {
        this.compilationCode = compilationCode;
    }

    public void setRunCode(Integer runCode)
    {
        this.runCode = runCode;
    }
}

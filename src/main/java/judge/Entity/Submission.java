package judge.Entity;

import javax.persistence.*;

@Entity
public class Submission
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne
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

    public Submission()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Student getAuthor()
    {
        return author;
    }

    public void setAuthor(Student author)
    {
        this.author = author;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public Integer getCompilationCode()
    {
        return compilationCode;
    }

    public void setCompilationCode(Integer compilationCode)
    {
        this.compilationCode = compilationCode;
    }

    public Integer getRunCode()
    {
        return runCode;
    }

    public void setRunCode(Integer runCode)
    {
        this.runCode = runCode;
    }
}

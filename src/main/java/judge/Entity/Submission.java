package judge.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Submission {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String code;
    private Integer compilationCode;
    private Integer runCode;

    public Submission(String code, int compilationCode, int runCode) {
        this.code = code;
        this.compilationCode = compilationCode;
        this.runCode = runCode;
    }

    public Submission() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCompilationCode() {
        return compilationCode;
    }

    public void setCompilationCode(int compilationCode) {
        this.compilationCode = compilationCode;
    }

    public int getRunCode() {
        return runCode;
    }

    public void setRunCode(int runCode) {
        this.runCode = runCode;
    }
}

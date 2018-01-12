package judge.Component;

public final class JudgeResult {
    private Integer compilationCode;
    private Integer runCode;
    private Integer testsPositive;
    private Integer testsTotal;
    private Float timeTaken;
    private String errorCode;

    public JudgeResult(Integer compilationCode, Integer runCode, Integer testsPositive, Integer testsTotal, Float timeTaken, String errorCode) {
        this.compilationCode = compilationCode;
        this.runCode = runCode;
        this.testsPositive = testsPositive;
        this.testsTotal = testsTotal;
        this.timeTaken = timeTaken;
        this.errorCode = errorCode;
    }

    public Integer getCompilationCode() {
        return compilationCode;
    }

    public void setCompilationCode(Integer compilationCode) {
        this.compilationCode = compilationCode;
    }

    public Integer getRunCode() {
        return runCode;
    }

    public void setRunCode(Integer runCode) {
        this.runCode = runCode;
    }

    public Integer getTestsPositive() {
        return testsPositive;
    }

    public void setTestsPositive(Integer testsPositive) {
        this.testsPositive = testsPositive;
    }

    public Integer getTestsTotal() {
        return testsTotal;
    }

    public void setTestsTotal(Integer testsTotal) {
        this.testsTotal = testsTotal;
    }

    public Float getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Float timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

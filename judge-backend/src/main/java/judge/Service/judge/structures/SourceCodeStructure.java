package judge.Service.judge.structures;

import java.util.List;

public class SourceCodeStructure {

    List<String> lines;
    /*
        For NEW_PROBLEM solution code is considered as custom code.
        For SUBMISSION code provided by submitting person is considered as custom code.
     */
    int lineWhereCustomCodeStarts;

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public int getLineWhereCustomCodeStarts() {
        return lineWhereCustomCodeStarts;
    }

    public void setLineWhereCustomCodeStarts(int lineWhereCustomCodeStarts) {
        this.lineWhereCustomCodeStarts = lineWhereCustomCodeStarts;
    }
}

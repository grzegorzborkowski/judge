package judge.Service.judge.structures;

import java.util.List;

public class SourceCodeStructure {

    List<String> lines;
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

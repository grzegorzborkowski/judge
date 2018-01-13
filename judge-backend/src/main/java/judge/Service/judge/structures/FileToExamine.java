package judge.Service.judge.structures;

public class FileToExamine {

    String filename;
    int lineWhereCustomCodeStarts;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getLineWhereCustomCodeStarts() {
        return lineWhereCustomCodeStarts;
    }

    public void setLineWhereCustomCodeStarts(int lineWhereCustomCodeStarts) {
        this.lineWhereCustomCodeStarts = lineWhereCustomCodeStarts;
    }
}

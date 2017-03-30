package judge.Service.judge;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

import static judge.TestUtils.HELLO_WORLD_SUBMISSION_CODE;
import static org.junit.Assert.assertTrue;

public class SourceCodeCreatorServiceTest {
    private SourceCodeCreatorService sourceCodeCreatorService;

    @Before
    public void init() {
        this.sourceCodeCreatorService = new SourceCodeCreatorService();
    }
    @Test
    public void testCreateSourceCodeFile() {
        String expectedFilePath = "test/resources/judge/Service/judge/source_code_01_ok.c";
        String submissionData = HELLO_WORLD_SUBMISSION_CODE;
        String outputFile = sourceCodeCreatorService.createSourceCodeFile(submissionData);
        File expectedFile = new File(expectedFilePath);
        File createdFile = new File(outputFile);

        try {
            assertTrue(FileUtils.contentEquals(expectedFile, createdFile));
        } catch (IOException e) {
            Assert.fail("Test failed: " + e.getMessage());
        } finally {
            createdFile.delete();
        }
    }
}

package judge.Service.judge;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static judge.Utils.*;

public class RunProgramServiceTest {
    private RunProgramService runProgramService;

    @Before
    public void init() {
        runProgramService = new RunProgramService();
    }
    @Test
    public void testRunProgramSuccess() {
        String programName = "test/resources/judge/Service/judge/runnable_program_01_ok.out";
        int generatedRunCode = runProgramService.runProgram(programName);

        try {
            Assert.assertEquals(RUN_SUCCESS_CODE, generatedRunCode);
        } catch (Exception e) {
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    @Test
    public void testRunProgramFailure() {
        String programName = "test/resources/judge/Service/judge/this_file_does_not_exist.out";
        int generatedRunCode = runProgramService.runProgram(programName);

        try {
            Assert.assertEquals(RUN_FAILURE_CODE, generatedRunCode);
        } catch (Exception e) {
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
    @Test
    public void testRunProgramTimeout() {
        String programName = "test/resources/judge/Service/judge/runnable_program_03_timeout.out";
        int generatedRunCode = runProgramService.runProgram(programName);

        try {
            Assert.assertEquals(TIMEOUT_CODE, generatedRunCode);
        } catch (Exception e) {
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}

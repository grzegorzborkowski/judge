package judge.Service.judge;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import static judge.Utils.*;

public class CompileServiceTest {
    private CompileService compileService;

    @Before
    public void init() {
        compileService = new CompileService();
    }
    @Test
    public void testCompileSourceCodeSuccess() {
        String filePath = "test/resources/judge/Service/judge/source_code_01_ok.c";
        String executableFilename = RUNTIME_DIR_NAME + "source_code_01_ok.c" + ".out";
        String compilationParams = "-o " + executableFilename;
        int generatedCompilationCode = compileService.compileSourceCode("gcc", compilationParams, filePath);
        Assert.assertEquals(COMPILATION_SUCCESS_CODE, generatedCompilationCode);
    }
    @Test
    public void testCompileSourceCodeFailure() {
        String filePath = "test/resources/judge/Service/judge/source_code_02_compilation_error.c";
        String executableFilename = RUNTIME_DIR_NAME + "source_code_02_compilation_error.c" + ".out";
        String compilationParams = "-o " + executableFilename;
        int generatedCompilationCode = compileService.compileSourceCode("gcc", compilationParams, filePath);
        Assert.assertEquals(COMPILATION_FAILURE_CODE, generatedCompilationCode);
    }
}

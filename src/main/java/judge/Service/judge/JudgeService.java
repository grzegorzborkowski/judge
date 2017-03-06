package judge.Service.judge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JudgeService {

    @Autowired
    SourceCodeCreatorService sourceCodeCreatorService;
    @Autowired
    CompileService compileService;
    @Autowired
    RunProgramService runProgramService;

    public int compileAndRun(String code) throws IOException {
        String filename = SourceCodeCreatorService.createSourceCodeFile(code);
        int compileResult = CompileService.compileSourceCode("gcc", filename);
        if(compileResult != -1) {
            int result = this.runProgramService.runProgram("a.out");
            return result;
        }
        return -1;
    }

}

package judge.Service.judge;

import org.json.simple.JSONObject;
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

    public JSONObject compileAndRun(String code) throws IOException {
        JSONObject result = new JSONObject();
        String filename = SourceCodeCreatorService.createSourceCodeFile(code);
        int compileResult = CompileService.compileSourceCode("gcc", filename);
        if(compileResult == 0) {
            result.put("exitCode",this.runProgramService.runProgram("a.out"));
        } else {
            result.put("exitCode",compileResult);
        }
        return result;
    }

}

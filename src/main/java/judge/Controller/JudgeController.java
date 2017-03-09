package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import judge.Service.judge.JudgeService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.io.StringReader;

@RestController
@RequestMapping("/judge")
@CrossOrigin
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody public String insertSolution(@RequestBody String submission) throws IOException {
        JsonNode rootNode = new ObjectMapper().readTree(new StringReader(submission));
        JsonNode codeNode = rootNode.get("code");
        String code = codeNode.asText();
        JSONObject result = this.judgeService.compileAndRun(code);
        return result.toString();
    }
}

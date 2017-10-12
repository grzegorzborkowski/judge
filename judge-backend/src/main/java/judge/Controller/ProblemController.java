package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import judge.Entity.Problem;
import judge.Service.ProblemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static judge.Utils.*;

@CrossOrigin
@RestController
@RequestMapping("/problems")
public class ProblemController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(StudentController.class);

    @Autowired
    private ProblemService problemService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<Problem> getAllProblems() {
        logger.info("Processing getAllProblems request");
        return this.problemService.getAllProblems();
    }

    @RequestMapping(value = "/getByID", method = RequestMethod.GET)
    public Problem getById(@RequestParam Integer id) {
        logger.info("Processing GET /problems/getByID");
        return this.problemService.getProblemById(id);
    }

    @RequestMapping(value = "/getTemplate", method = RequestMethod.GET)
    public String getTemplate() {
        logger.info("Processing GET /problems/getTemplate");
        String structures = "", solution = "";
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(TEMPLATES_DIR_NAME + TEACHERS_TEMPLATE_1_NAME));
            structures = String.join("\n", lines);

            lines = Files.readAllLines(Paths.get(TEMPLATES_DIR_NAME + TEACHERS_TEMPLATE_2_NAME));
            solution = String.join("\n", lines);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("structures", structures);
        result.put("solution", solution);
        return result.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addProblem(@RequestBody JsonNode problemJson){
        logger.info("Processing POST /problems/add");
        logger.info("New problem received.");
        Problem problem = new Problem();
        problem.setTitle(problemJson.get("title").asText());
        problem.setDescription(problemJson.get("description").asText());
        problem.setStructures(problemJson.get("structures").asText());
        problem.setSolution(problemJson.get("solution").asText());
        return this.problemService.addProblem(problem);
    }
}

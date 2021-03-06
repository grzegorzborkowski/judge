package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import judge.Entity.Category;
import judge.Entity.Problem;
import judge.Entity.User;
import judge.Service.CategoryService;
import judge.Service.ProblemService;
import judge.Service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

import static judge.Utils.*;

@CrossOrigin
@RestController
@RequestMapping("/problems")
public class ProblemController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(ProblemController.class);

    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

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

    @RequestMapping(value = "/getByCategory", method = RequestMethod.GET)
    public List<Problem> getByCategory(@RequestParam String name) {
        logger.info("Processing GET /problems/getByCategory");
        return this.problemService.getProblemsByCategoryName(name);
    }

    @RequestMapping(value = "/getTemplate", method = RequestMethod.GET)
    public String getTemplate() {
        logger.info("Processing GET /problems/getTemplate");
        String structures = "", solution = "";
        List<String> lines;

        try {
            lines = Files.readAllLines(Paths.get(TEMPLATES_DIR_NAME + STRUCTURES_C));
            structures = String.join("\n", lines);

            lines = Files.readAllLines(Paths.get(TEMPLATES_DIR_NAME + TEACHERS_FUNCTION_C));
            solution = String.join("\n", lines);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("structures", structures);
        result.put("solution", solution);
        return result.toString();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeProblemById(@RequestParam Integer id) {
        logger.info("Processing POST /problems/remove");
        return problemService.removeProblem(id);
    }

    /**
     *
     * @param problemJson [description, title, structures, solution]
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addProblem(@RequestBody JsonNode problemJson) {
        logger.info("Processing POST /problems/add");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        Category category = categoryService.findByName(problemJson.get("category").asText());

        Problem problem = new Problem(user, category, problemJson.get("title").asText(), problemJson.get("description").asText(), problemJson.get("structures").asText(), problemJson.get("solution").asText());
        String status = this.problemService.addProblem(problem);
        return status;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String editProblem(@RequestBody JsonNode problemJson) {
        logger.info("Processing POST /problems/edit");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);

        Category category = categoryService.findByName(problemJson.get("category").asText());

        Problem problem = this.problemService.getProblemById(Integer.parseInt(problemJson.get("id").asText()));
        problem.setAuthor(user);
        problem.setDescription(problemJson.get("description").asText());
        problem.setTitle(problemJson.get("title").asText());
        problem.setStructures(problemJson.get("structures").asText());
        problem.setSolution(problemJson.get("solution").asText());
        problem.setCategory(category);
        String status = this.problemService.saveProblem(problem);
        return status;
    }
}

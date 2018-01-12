package judge.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import judge.Entity.Category;
import judge.Service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addCategory(@RequestBody JsonNode categoryJson) {
        logger.info("Processing POST /categories/add");

        String categoryName = categoryJson.get("name").asText();
        Category category = new Category(categoryName);
        String status = this.categoryService.addCategory(category);
        return status;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<Category> getAllCategories() {
        logger.info("Processing getAllCategories request");
        return this.categoryService.getAllCategories();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String removeCategoryById(@RequestParam Integer id) {
        logger.info("Processing POST /categories/remove");
        return categoryService.removeCategory(id);
    }

    @RequestMapping(value = "/changeName", method = RequestMethod.POST)
    public String changeCategoryNameById(@RequestParam Integer id,
                                         @RequestBody JsonNode json) {
        logger.info("Processing POST /categories/changeName");
        String newName = json.get("name").asText();
        return categoryService.changeCategoryName(id, newName);
    }
}

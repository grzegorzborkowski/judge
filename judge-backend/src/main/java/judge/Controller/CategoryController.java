package judge.Controller;

import judge.Entity.Category;
import judge.Entity.Problem;
import judge.Service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private static org.apache.log4j.Logger logger = Logger.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<Category> getAllCategories() {
        logger.info("Processing getAllCategories request");
        return this.categoryService.getAllCategories();
    }
}

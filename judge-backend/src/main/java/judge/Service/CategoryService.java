package judge.Service;

import judge.Dao.CategoryDao;
import judge.Entity.Category;
import judge.Entity.Problem;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Configurable
@EnableTransactionManagement
public class CategoryService {
    private static org.apache.log4j.Logger logger = Logger.getLogger(CategoryService.class);

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ProblemService problemService;

    public Collection<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        Iterable<Category> categories = categoryDao.findAll();
        categories.forEach(categoryList::add);
        return categoryList;
    }

    public Category findByName(String name) {
        Category category = categoryDao.findByName(name);
        return category;
    }
    public Category findById(Integer id) {
        Category category = categoryDao.findById(id);
        return category;
    }

    public String addCategory(Category category) {
        if(this.categoryDao.findByName(category.getName()) == null) {
            this.categoryDao.save(category);
            return "New category has been added";
        } else {
            logger.warn("Category with the same title already exists.");
            return "Category with the same name already exists. Adding failed.";
        }
    }

    @Transactional
    public String removeCategory(Integer id) {
        if(this.categoryDao.findById(id) != null) {
            int referringProblems = 0;
            for(Problem p : this.problemService.getProblemsByCategoryId(id)) {
                referringProblems++;
                this.problemService.removeProblem(p.getId());
            }
            this.categoryDao.removeById(id);
            return "Category and its " + referringProblems + " referring problem(s) have been removed";
        } else {
            return "Category with the given id doesn't exist";
        }
    }

    public String changeCategoryName(Integer id, String newName) {
        Category category = this.categoryDao.findById(id);
        if (category != null) {
            category.setName(newName);
            categoryDao.save(category);
            return "Category name updated";
        } else {
            return "Category with the given id doesn't exists";
        }
    }
}

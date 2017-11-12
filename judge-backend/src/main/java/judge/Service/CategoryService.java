package judge.Service;

import judge.Dao.CategoryDao;
import judge.Entity.Category;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
}

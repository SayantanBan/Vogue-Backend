package tk.sayantan.Vogue.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.sayantan.Vogue.exception.UserNotFoundException;
import tk.sayantan.Vogue.model.Category;
import tk.sayantan.Vogue.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = {"https://vogue-dev.herokuapp.com", "http://localhost:4200"})
public class CategoryResource {

    private static final Logger LOG = LoggerFactory.getLogger(SecuredResource.class);

    @Autowired
    private CategoryService service;

    @GetMapping(value = "/category")
    public List<Category> retrieveAllCategories() {
        LOG.info("> retrieveAllCategories");
        List<Category> categories = null;
        try {
            categories = service.findAllCategory();
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("< retrieveAllCategories");
        return categories;
    }

    @GetMapping(value = "/category/{id}")
    public Category retrieveCategory(@PathVariable int id) {
        LOG.info("> retrieveCategory");
        Category category = null;
        try {
            Optional<Category> optionalCategory = service.findOne(id);
            if (optionalCategory.isPresent())
                category = optionalCategory.get();
            else
                throw new UserNotFoundException("id-" + id);
        } catch (UserNotFoundException e) {
            LOG.error("Category not found: {} {}", e, e.getMessage());
        } catch (Exception e) {
            LOG.error("Unexpected System Error: {} {}", e, e.getMessage());
        }
        LOG.info("> retrieveCategory");
        return category;
    }
}

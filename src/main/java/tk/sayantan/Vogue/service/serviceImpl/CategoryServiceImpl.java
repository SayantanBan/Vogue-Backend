package tk.sayantan.Vogue.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.sayantan.Vogue.model.Category;
import tk.sayantan.Vogue.repository.CategoryRepository;
import tk.sayantan.Vogue.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Override
    public Category findBycategoryName(String title) {
        return repository.findBycategoryName(title);
    }

    @Override
    public List<Category> findAllCategory() {
        return (List<Category>) repository.findAll();
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Optional<Category> findOne(int id) {
        return repository.findById((long) id);
    }

}

package tk.sayantan.Vogue.service;

import tk.sayantan.Vogue.model.Category;

import java.util.List;
import java.util.Optional;


public interface CategoryService {
    Category findBycategoryName(String title);

    List<Category> findAllCategory();

    Category save(Category category);

    Optional<Category> findOne(int id);
}

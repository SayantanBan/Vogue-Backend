package tk.sayantan.Vogue.repository;

import org.springframework.data.repository.CrudRepository;
import tk.sayantan.Vogue.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findBycategoryName(String name);

}

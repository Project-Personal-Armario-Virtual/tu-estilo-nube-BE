package dev.yeferson.tu_estilo_nube_BE.category;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(String name) {
        Category category = new Category(name);
        return categoryRepository.save(category);
    }
    

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
    
  
    public Category updateCategory(Long id, String name) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(name);
        return categoryRepository.save(category);
    }
    

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}

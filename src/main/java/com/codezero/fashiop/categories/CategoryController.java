package com.codezero.fashiop.categories;

import com.codezero.fashiop.categories.entity.Category;
import com.codezero.fashiop.categories.service.CategoryService;
import com.codezero.fashiop.shared.route.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Routes.CATEGORIES)
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public Page<Category> getAllCategories(Pageable page) {
        return categoryService.getAllCategories(page);
    }

    @GetMapping("/{categoryId}")
    public Category getCategory(@PathVariable("categoryId") long categoryId) {
        return categoryService.getById(categoryId);
    }

    @PostMapping
    public Category saveCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @DeleteMapping("/{categoryId}")
    public Category deleteCategory(@PathVariable long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/count")
    public long getCategoriesCount() { return categoryService.getCategoriesCount(); }
}

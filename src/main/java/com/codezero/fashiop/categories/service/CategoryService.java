package com.codezero.fashiop.categories.service;

import com.codezero.fashiop.categories.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {

    Page<Category> getAllCategories(Pageable page);

    Category getById(long categoryId);

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    Category deleteCategory(long categoryId);

    long getCategoriesCount();
}

package com.codezero.fashiop.categories.service.impl;

import com.codezero.fashiop.categories.dao.CategoryDao;
import com.codezero.fashiop.categories.entity.Category;
import com.codezero.fashiop.categories.exception.CategoryException;
import com.codezero.fashiop.categories.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public Page<Category> getAllCategories(Pageable page) {
        return categoryDao.findAll(page);
    }

    @Override
    public Category getById(long categoryId) {
        return categoryDao.findById(categoryId)
                .orElseThrow((() -> new CategoryException(HttpStatus.CONFLICT.value(), "Category not found.")));
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public Category deleteCategory(long categoryId) {
        Category category = categoryDao.findById(categoryId)
                .orElseThrow((() -> new CategoryException(HttpStatus.CONFLICT.value(), "Category not found.")));
        categoryDao.delete(category);

        return category;
    }

    @Override
    public long getCategoriesCount() {
        return categoryDao.count();
    }
}

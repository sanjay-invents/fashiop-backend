package com.codezero.fashiop.categories.dao;

import com.codezero.fashiop.categories.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends PagingAndSortingRepository<Category, Long> {
}

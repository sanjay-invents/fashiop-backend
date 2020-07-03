package com.codezero.fashiop.products.dao;

import com.codezero.fashiop.products.entity.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductDao extends PagingAndSortingRepository<Product, Long> {
}

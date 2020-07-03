package com.codezero.fashiop.products.service;

import com.codezero.fashiop.products.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    Page<Product> getAllProducts(Pageable page);

    Product getById(long productId);

    Product saveProduct(Product product);

    Product saveProductImage(MultipartFile file, long productId);

    Product updateProduct(Product product);

    Product deleteProduct(long productId);

    long getProductsCount();
}

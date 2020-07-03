package com.codezero.fashiop.products.service.impl;

import com.codezero.fashiop.categories.exception.CategoryException;
import com.codezero.fashiop.products.dao.ProductDao;
import com.codezero.fashiop.products.entity.Product;
import com.codezero.fashiop.products.service.ProductService;
import com.codezero.fashiop.shared.util.FileUploaderUtil;
import com.codezero.fashiop.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.codezero.fashiop.shared.model.Constants.PRODUCT_PICTURE_UPLOAD_FOLDER;
import static com.codezero.fashiop.shared.model.Constants.USER_PROFILE_PICTURE_UPLOAD_FOLDER;

@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public Page<Product> getAllProducts(Pageable page) {
        return productDao.findAll(page);
    }

    @Override
    public Product getById(long productId) {
        return productDao.findById(productId)
                .orElseThrow((() -> new CategoryException(HttpStatus.CONFLICT.value(), "Product not found.")));
    }

    @Override
    public Product saveProduct(Product product) {
        return productDao.save(product);
    }

    @Override
    public Product saveProductImage(MultipartFile file, long productId) {
        Product product = getById(productId);
        product.setProductPicture(FileUploaderUtil.uploadImage(file, product.getName(), PRODUCT_PICTURE_UPLOAD_FOLDER));
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        Product productData = getById(product.getId());
        productData.setName(product.getName());
        productData.setDescription(product.getDescription());
        productData.setPrice(product.getPrice());
        productData.setQuantity(product.getQuantity());
        productData.setStartDate(product.getStartDate());
        productData.setEndDate(product.getEndDate());
        productData.setCategory(product.getCategory());
        return productDao.save(productData);
    }

    @Override
    public Product deleteProduct(long productId) {
        Product product = getById(productId);
        if (product == null) {
            return null;
        } else {
            if(!FileUploaderUtil.deleteImage(PRODUCT_PICTURE_UPLOAD_FOLDER, product.getProductPicture())) {
                System.out.println("File delete not successful. Please delete manually");
            }
            productDao.delete(product);
        }
        return product;
    }

    @Override
    public long getProductsCount() {
        return productDao.count();
    }

}

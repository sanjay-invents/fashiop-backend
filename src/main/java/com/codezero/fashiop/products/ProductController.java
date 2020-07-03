package com.codezero.fashiop.products;

import com.codezero.fashiop.products.entity.Product;
import com.codezero.fashiop.products.service.ProductService;
import com.codezero.fashiop.shared.route.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(Routes.PRODUCTS)
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public Page<Product> getAllProducts(Pageable page) {
        return productService.getAllProducts(page);
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable("productId") long productId) {
        return productService.getById(productId);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PostMapping("/{productId}")
    public Product saveProductPicture(@RequestParam(value="file") MultipartFile file, @PathVariable long productId) {
        return productService.saveProductImage(file, productId);
    }

    @GetMapping("/count")
    public long getProductsCount() { return productService.getProductsCount(); }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/{productId}")
    public Product deleteProduct(@PathVariable long productId) {
        return productService.deleteProduct(productId);
    }

}

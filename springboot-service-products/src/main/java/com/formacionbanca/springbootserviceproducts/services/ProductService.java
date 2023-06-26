package com.formacionbanca.springbootserviceproducts.services;



import com.formacionbanca.springbootservicecommons.models.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> findAllProducts();
    public Product findProductById(Long ProductId);

    public Product saveProduct(Product product);

    public void deleteProductById(Long productId);

    public Product updateProduct(Long productId, Product product);
}

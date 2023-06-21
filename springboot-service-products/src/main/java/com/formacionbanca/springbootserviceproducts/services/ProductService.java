package com.formacionbanca.springbootserviceproducts.services;

import com.formacionbanca.springbootserviceproducts.models.entities.Product;

import java.util.List;

public interface ProductService {

    public List<Product> findAllProducts();
    public Product findProductById(Long ProductId);


}

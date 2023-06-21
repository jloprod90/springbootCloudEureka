package com.formacionbanca.springbootserviceproducts.controllers;

import com.formacionbanca.springbootserviceproducts.models.entities.Product;
import com.formacionbanca.springbootserviceproducts.services.ProductService;
import com.formacionbanca.springbootserviceproducts.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apiProducts")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;


    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId) {
        return productService.findProductById(productId);
    }



}

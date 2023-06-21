package com.formacionbanca.springbootserviceproducts.controllers;

import com.formacionbanca.springbootserviceproducts.models.entities.Product;
import com.formacionbanca.springbootserviceproducts.services.ProductService;
import com.formacionbanca.springbootserviceproducts.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    // inyect env (port) from application properties
    @Value("${server.port}")
    private Integer port;

    @Autowired
    private ProductServiceImpl productService;


    @GetMapping("/products")
    public List<Product> getProducts() {

        return productService.findAllProducts().stream().peek(p -> p.setPort(port)).collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId) {
        Product product =productService.findProductById(productId);
        product.setPort(port);
        return product;
    }



}

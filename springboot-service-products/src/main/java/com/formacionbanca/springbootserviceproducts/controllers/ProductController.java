package com.formacionbanca.springbootserviceproducts.controllers;


import com.formacionbanca.springbootservicecommons.models.entity.Product;
import com.formacionbanca.springbootserviceproducts.services.ProductService;
import com.formacionbanca.springbootserviceproducts.services.ProductServiceImpl;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    // inyect env (port) from application properties
    @Autowired
    private Environment env;

    //@Value("${server.port}")
    //private Integer port;

    @Autowired
    private ProductServiceImpl productService;


    @GetMapping("/products")
    public List<Product> getProducts() {

        return productService.findAllProducts().stream().map(p -> {
            //p.setPort(port);
            p.setPort(Integer.parseInt(env.getProperty("local.server.port")));
            return p;
        }).collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId) throws InterruptedException {

        //simulación de errores
        if(productId.equals(10L)) {
            throw new IllegalStateException("Producto no encontrado");
        }
        if(productId.equals(7L)) {
            TimeUnit.SECONDS.sleep(5L);
        }

        Product product = productService.findProductById(productId);
        product.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("local.server.port"))));
        //product.setPort(port);
        return product;
    }

    @PostMapping("/products/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }


    @DeleteMapping("/products/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable ("id") Long productId) {
        productService.deleteProductById(productId);
    }

    @PutMapping("/products/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteProductById(@RequestBody Product product, @PathVariable ("id") Long productId) {
        productService.updateProduct(productId, product);
    }



}

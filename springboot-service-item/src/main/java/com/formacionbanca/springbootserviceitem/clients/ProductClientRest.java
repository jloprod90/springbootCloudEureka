package com.formacionbanca.springbootserviceitem.clients;

import com.formacionbanca.springbootserviceitem.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(name ="service-products") // hacemos referencia por el nombre del servicio porque tenemos multiplex instancias.
public interface ProductClientRest {

    @GetMapping("/products")
    public List<Product> getProducts();

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId);

    @PostMapping("/products/create")
    public Product createProduct(@RequestBody Product product);

    @PutMapping("/products/update/{id}")
    public Product updateProductById(@RequestBody Product product, @PathVariable("id") Long productId);


    @DeleteMapping("/products/delete/{id}")
    public Product deleteProductById(@PathVariable("id") Long productId);

}

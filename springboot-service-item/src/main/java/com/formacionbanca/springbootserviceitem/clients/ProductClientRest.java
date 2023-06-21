package com.formacionbanca.springbootserviceitem.clients;

import com.formacionbanca.springbootserviceitem.models.Item;
import com.formacionbanca.springbootserviceitem.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "service-products", url="http://localhost:8003/apiProducts")
public interface ProductClientRest {

    @GetMapping("/products")
    public List<Product> getProducts();

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId);
}

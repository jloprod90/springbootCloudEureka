package com.formacionbanca.springbootserviceitem.clients;

import com.formacionbanca.springbootserviceitem.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@FeignClient(name ="service-products") // hacemos referencia por el nombre del servicio porque tenemos multiplex instancias.
public interface ProductClientRest {

    @GetMapping("/products")
    public List<Product> getProducts();

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long productId);
}

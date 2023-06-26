package com.formacionbanca.springbootserviceitem.services;

import com.formacionbanca.springbootserviceitem.models.Item;
import com.formacionbanca.springbootserviceitem.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service("itemServiceRestTemplate")
public class ItemServiceRestTemplate implements ItemService{
    @Autowired
    private RestTemplate restClient;

    @Override
    public List<Item> findAll() {
        List<Product> products = Arrays.asList(Objects.requireNonNull(restClient.getForObject("http://service-products/products", Product[].class)));
        return products.stream().map(p -> new Item(p,1)).collect(Collectors.toList());

    }

    @Override
    public Item findById(Long id, Integer amount) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        Product product = restClient.getForObject("http://service-products/products/{id}", Product.class, pathVariables );
        return new Item(product,amount);
    }

    @Override
    public Product createProduct(Product product) {

        HttpEntity<Product> body = new HttpEntity<Product>(product);
        ResponseEntity<Product> response = restClient.exchange("http://service-products/products/create", HttpMethod.POST, body, Product.class);
        return response.getBody();
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", productId.toString());

        HttpEntity<Product> body = new HttpEntity<Product>(product);
        ResponseEntity<Product> response = restClient.exchange("http://service-products/products/update/{id}", HttpMethod.PUT, body, Product.class, pathVariables);
        return response.getBody();
    }

    @Override
    public void deleteProduct(Long productId) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", productId.toString());

        restClient.delete("http://service-products/products/delete/{id}",pathVariables);
    }
}

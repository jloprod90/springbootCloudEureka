package com.formacionbanca.springbootserviceitem.services;

import com.formacionbanca.springbootserviceitem.models.Item;
import com.formacionbanca.springbootserviceitem.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Product> products = Arrays.asList(Objects.requireNonNull(restClient.getForObject("http://localhost:8003/apiProducts/products", Product[].class)));
        return products.stream().map(p -> new Item(p,1)).collect(Collectors.toList());

    }

    @Override
    public Item findById(Long id, Integer amount) {
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("id", id.toString());
        Product product = restClient.getForObject("http://localhost:8003/apiProducts/products/{id}", Product.class, pathVariables );
        return new Item(product,amount);
    }
}

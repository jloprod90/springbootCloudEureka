package com.formacionbanca.springbootserviceitem.services;

import com.formacionbanca.springbootservicecommons.models.entity.Product;
import com.formacionbanca.springbootserviceitem.models.Item;

import java.util.List;

public interface ItemService {

    public List<Item> findAll();

    public Item findById(Long id, Integer amount);

    public Product createProduct(Product product);
    public Product updateProduct(Product product, Long productId);
    public void deleteProduct(Long productId);


}

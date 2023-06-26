package com.formacionbanca.springbootserviceitem.services;

import com.formacionbanca.springbootserviceitem.clients.ProductClientRest;
import com.formacionbanca.springbootserviceitem.models.Item;
import com.formacionbanca.springbootserviceitem.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service("itemServiceFeign")
@Primary
public class ItemServiceFeign implements ItemService{

    @Autowired
    private ProductClientRest productClientRest;

    @Override
    public List<Item> findAll() {
        return productClientRest.getProducts().stream().map(p -> new Item(p,1)).collect(Collectors.toList());
    }

    @Override
    public Item findById(Long id, Integer amount) {
        return new Item(productClientRest.getProductById(id),amount);
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product, Long productId) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }
}

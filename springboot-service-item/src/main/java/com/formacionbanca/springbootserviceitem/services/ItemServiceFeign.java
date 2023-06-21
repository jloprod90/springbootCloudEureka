package com.formacionbanca.springbootserviceitem.services;

import com.formacionbanca.springbootserviceitem.clients.ProductClientRest;
import com.formacionbanca.springbootserviceitem.models.Item;
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
}

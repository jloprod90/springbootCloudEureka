package com.formacionbanca.springbootserviceitem.services;

import com.formacionbanca.springbootserviceitem.models.Item;

import java.util.List;

public interface ItemService {

    public List<Item> findAll();

    public Item findById(Long id, Integer amount);
}

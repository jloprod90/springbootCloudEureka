package com.formacionbanca.springbootserviceitem.controllers;

import com.formacionbanca.springbootserviceitem.models.Item;
import com.formacionbanca.springbootserviceitem.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("apiItems")
public class ItemController {
    @Autowired
    private ItemService itemService;


    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemService.findAll();
    }


    @GetMapping("/items/{id}/cantidad/{amount}")
    public Item getItemById(@PathVariable Long id, @PathVariable Integer amount) {
        return itemService.findById(id,amount);
    }

}

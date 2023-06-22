package com.formacionbanca.springbootserviceitem.controllers;

import com.formacionbanca.springbootserviceitem.models.Item;
import com.formacionbanca.springbootserviceitem.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    @Autowired
    @Qualifier("itemServiceFeign")
    private ItemService itemService;


    @GetMapping("/items")
    public List<Item> getAllItems(@RequestParam (name = "name", required = false) String name, @RequestHeader(name = "token-request", required = false) String token) {
        System.out.println(name);
        System.out.println(token);
        return itemService.findAll();
    }

    @GetMapping("/prueba")
    public String prueba() {
        return "hola hola";
    }


    @GetMapping("/items/{id}/amount/{amount}")
    public Item getItemById(@PathVariable Long id, @PathVariable Integer amount) {
        return itemService.findById(id,amount);
    }

}

package com.formacionbanca.springbootserviceitem.controllers;

import com.formacionbanca.springbootserviceitem.models.Item;
import com.formacionbanca.springbootserviceitem.models.Product;
import com.formacionbanca.springbootserviceitem.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {


    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;



    @Autowired
    @Qualifier("itemServiceFeign")
    private ItemService itemService;

    public ItemController(CircuitBreakerFactory circuitBreakerFactory, ItemService itemService) {
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.itemService = itemService;
    }


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
        return circuitBreakerFactory.create("items")
                .run(() -> itemService.findById(id,amount) /*, e -> alternativeMethod(id, amount, e)*/);
    }

    public Item alternativeMethod(Long id, Integer amount, Throwable e) {

        logger.info(e.getMessage());

        Item item = new Item();
        Product product = new Product();
        item.setAmount(amount);
        product.setId(id);
        product.setName("Camara Sony");
        product.setPrice(500.00);
        item.setProduct(product);
        return item;
    }

}

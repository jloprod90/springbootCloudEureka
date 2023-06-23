package com.formacionbanca.springbootserviceitem.controllers;

import com.formacionbanca.springbootserviceitem.models.Item;
import com.formacionbanca.springbootserviceitem.models.Product;
import com.formacionbanca.springbootserviceitem.services.ItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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


    @GetMapping("/items/{id}/amount/{amount}") // configuracion manual circuitBreaker
    public Item getItemById(@PathVariable Long id, @PathVariable Integer amount) {
        return circuitBreakerFactory.create("items")
                .run(() -> itemService.findById(id,amount) , e -> alternativeMethod(id, amount, e));
    }

    @CircuitBreaker(name="items", fallbackMethod = "alternativeMethod") // configuracion automática, necesita configuración por application.yml o application.properties
    @GetMapping("/items2/{id}/amount/{amount}")
    public Item getItemById2(@PathVariable Long id, @PathVariable Integer amount) {
        return itemService.findById(id,amount);
    }

    @CircuitBreaker(name="items", fallbackMethod="alternativeMethod2") // configuracion automática, necesita configuración por application.yml o application.properties
    @TimeLimiter(name="items") // configuracion automática, necesita configuración por application.yml o application.properties
    //@TimeLimiter(name="items", fallbackMethod="alternativeMethod2") // con método alternativo si no usa @CircuiteBraker, si lo usa solo ponerlo en el primero
    @GetMapping("/items3/{id}/amount/{amount}")
    public CompletableFuture<Item> getItemById3(@PathVariable Long id, @PathVariable Integer amount) {
        return CompletableFuture.supplyAsync(() -> itemService.findById(id,amount));
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

    public CompletableFuture<Item> alternativeMethod2(Long id, Integer amount, Throwable e) {

        logger.info(e.getMessage());

        Item item = new Item();
        Product product = new Product();
        item.setAmount(amount);
        product.setId(id);
        product.setName("Camara Sony");
        product.setPrice(500.00);
        item.setProduct(product);
        return CompletableFuture.supplyAsync(() -> item);
    }

}

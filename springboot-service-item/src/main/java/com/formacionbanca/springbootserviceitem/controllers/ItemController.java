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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
@RefreshScope
@RestController
public class ItemController {

    private static Logger logger = LoggerFactory.getLogger(ItemController.class);


    @Value("${configuration.text}")
    private String text;

    @Autowired
    private CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private Environment env;



    @Autowired
    //@Qualifier("itemServiceFeign")
    @Qualifier("itemServiceRestTemplate")
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


    @GetMapping("/get-conf")
    public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {
        logger.info(text);

        Map<String, String> json = new HashMap<>();
        json.put("text",text);
        json.put("port",port);

        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.name", env.getProperty("configuration.autor.name"));
            json.put("autor.email", env.getProperty("configuration.autor.email"));
        }

        return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
    }

    // GET "/actuator" -> saca la info.
    // POST "/actuator/refresh" -> actualiza la configuración automaticamente sin reiniciar el servicio, debe tener el @RefreshScope y configuración.


    @PostMapping("/create-product")
    public Product createProduct(@RequestBody Product product) {
        return itemService.createProduct(product);
    }

    @PutMapping("/update-product/{id}")
    public Product createProduct(@RequestBody Product product, @PathVariable ("id") Long productId) {
        return itemService.updateProduct(product, productId);
    }

    @DeleteMapping("/delete-product/{id}")
    public void deleteProduct(@PathVariable ("id") Long productId) {
        itemService.deleteProduct(productId);
    }
}

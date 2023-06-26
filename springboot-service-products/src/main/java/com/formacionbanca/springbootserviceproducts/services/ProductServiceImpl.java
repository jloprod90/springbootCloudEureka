package com.formacionbanca.springbootserviceproducts.services;


import com.formacionbanca.springbootservicecommons.models.entity.Product;
import com.formacionbanca.springbootserviceproducts.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    @Transactional(readOnly = true)
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product productDb = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No existe el producto con ID: " + productId));

        productDb.setName(product.getName());
        productDb.setPrice(productDb.getPrice());

        return productRepository.save(productDb);

    }
}

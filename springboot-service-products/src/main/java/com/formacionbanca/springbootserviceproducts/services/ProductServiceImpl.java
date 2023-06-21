package com.formacionbanca.springbootserviceproducts.services;

import com.formacionbanca.springbootserviceproducts.models.entities.Product;
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
}

package com.formacionbanca.springbootserviceproducts.repositories;

import com.formacionbanca.springbootserviceproducts.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}

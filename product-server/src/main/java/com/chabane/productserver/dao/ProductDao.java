package com.chabane.productserver.dao;

import com.chabane.productserver.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {
    Product findById(int id);
    List<Product> findAll();
    List<Product> findByCategoriesContains(String id);
    void deleteById(int id);
}

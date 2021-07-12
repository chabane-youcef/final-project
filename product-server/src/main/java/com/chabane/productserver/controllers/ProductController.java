package com.chabane.productserver.controllers;

import com.chabane.productserver.dao.ProductDao;
import com.chabane.productserver.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private Environment environment;

    //TODO: refactor this composition to gateway server
    @GetMapping(value = "products")
    public List<Product> allProducts() {

        List<Product> products = productDao.findAll();

        return products;
    }

    @GetMapping(value = "products/{id}")
    public Product show(@PathVariable("id") int id) {
        Product product = productDao.findById(id);
        System.out.println(product);
        product.setImage(getImageFullUrl(product.getImage()));
        return product;
    }


    @GetMapping(value = "category/products/{id}")
    public List<Product> categoryProducts(@PathVariable("id") String id) {
        List<Product> products = productDao.findAll();
        System.out.println(products);
        for(int i = 0; i < products.size(); i++) {
            if (!isInCategory(id, products.get(i).getCategories())) {
                products.remove(products.get(i));
            }
        }

        for(Product product: products) {
            product.setImage(getImageFullUrl(product.getImage()));
        }

        return products;
    }

    private boolean isInCategory(String categoryId, String productCategories) {

        if(productCategories.length() == 1)
            return categoryId.equals(productCategories);

        List<String> list = Arrays.asList(productCategories.split(","));
        return list.contains(categoryId);
    }

    @PostMapping(value = "products/search")
    public List<Product> getProductsByIDs(@RequestBody List<Integer> ids) {

        System.out.println(ids);
        List<Product> products = productDao.findAll();
        List<Product> filteredProducts = new ArrayList<>();

        for(Product p : products) {
            if(ids.indexOf(p.getId()) >= 0) {
                filteredProducts.add(p);
            }
        }

        return filteredProducts;
    }

    @PostMapping(value = "products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product store(@RequestBody Product product) {
        return productDao.save(product);
    }

    @PutMapping(value = "products")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@RequestBody Product product) {
        productDao.save(product);
    }

    @DeleteMapping(value = "products/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void destroy(@PathVariable("id") int id) {
        productDao.deleteById(id);
    }

    private String getImageFullUrl(String imageUrl) {
        String port = environment.getProperty("local.server.port");
        String host = "http://localhost";

        try {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            ;
        }

        return "http://"+host + ":" + port + "/images/" + imageUrl;
    }
}

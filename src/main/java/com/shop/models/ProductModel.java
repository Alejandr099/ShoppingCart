package com.shop.models;

import java.util.ArrayList;
import java.util.List;

import com.shop.shoppingcart.Product;

public class ProductModel {

    public List<Product> findAll() {
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("p1", "Name 1", 100));
        products.add(new Product("p2", "Name 2", 200));
        products.add(new Product("p3", "Name 3", 300));
        return products;
    }

    public Product find(String id) {
        for (Product product : findAll()) {
            if (product.getId().equalsIgnoreCase(id)) {
                return product;
            }
        }
        return null;
    }

}
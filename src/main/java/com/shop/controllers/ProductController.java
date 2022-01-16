package com.shop.controllers;


import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.shop.models.ProductModel;

@Controller
@RequestMapping(value = { "", "product" })
public class ProductController {

    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        ProductModel productModel = new ProductModel();
        modelMap.put("products", productModel.findAll());
        return "product/index";
    }

}
package com.shop.controllers;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.shoppingcart.Item;
import com.shop.models.ProductModel;

@Controller
@RequestMapping(value = "cart")
public class CartController {

    private int isExist(String id, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    @RequestMapping(value = "buy/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> buy(@PathVariable(value = "id") String id, HttpSession session) {
        ProductModel productModel = new ProductModel();
        if (session.getAttribute("cart") == null) {
            List<Item> cart = new ArrayList<Item>();
            cart.add(new Item(productModel.find(id), 1));
            session.setAttribute("cart", cart);
        } else {
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            int index = this.isExist(id, session);
            if (index != -1) {
                cart.get(index).setQuantity(cart.get(index).getQuantity() + 1);
            }
            else {
                cart.add(new Item(productModel.find(id), 1));
            }
            session.setAttribute("cart", cart);
        }
        return (List<Item>) session.getAttribute("cart");
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> delete(@PathVariable(value = "id") String id, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int index = isExist(id, session);
        cart.remove(index);
        session.setAttribute("cart", cart);
        return (List<Item>) session.getAttribute("cart");
    }

}
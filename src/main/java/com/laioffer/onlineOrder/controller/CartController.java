package com.laioffer.onlineOrder.controller;
import com.laioffer.onlineOrder.serice.CartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.laioffer.onlineOrder.entity.Cart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartController {

    @Autowired //自动链接，就是@service（一种特殊的component，因为spring frame work需要特殊的作用，是service就@service）
    private CartService cartService;


    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    @ResponseBody
    public Cart getCart(){
        return cartService.getCart();//没有登陆的人是没发走到这一步的

    }
}

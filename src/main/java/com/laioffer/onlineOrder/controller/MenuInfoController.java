package com.laioffer.onlineOrder.controller;

import com.laioffer.onlineOrder.entity.MenuItem;
import com.laioffer.onlineOrder.entity.Restaurant;
import com.laioffer.onlineOrder.serice.MenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

import java.util.List;
/*
@Controller
public class MenuInfoController {
    // 定义在{}里面的值要和PathVariable里面的对应
    // 这样框架就会自己去找对应的值。
    @RequestMapping(value = "/restaurant/{restaurantId}/menu", method = RequestMethod.GET)
    @ResponseBody
    public List<MenuItem> getMenus(@PathVariable("restaurantId") int restaurantId) {
        return new ArrayList<>();
    }

    @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
    @ResponseBody
    public List<Restaurant> getRestaurants() {
        return new ArrayList<>();
    }
}
*/
@Controller

public class MenuInfoController {

        @Autowired
        private MenuInfoService menuInfoService;

        @RequestMapping(value = "/restaurants", method = RequestMethod.GET)
        @ResponseBody
        public List<Restaurant> getRestaurants() {
            return menuInfoService.getRestaurants();
        }

        @RequestMapping(value = "/restaurant/{restaurantId}/menu", method = RequestMethod.GET)
        @ResponseBody
        public List<MenuItem> getMenus(@PathVariable(value = "restaurantId") int restaurantId) {
            return menuInfoService.getAllMenuItem(restaurantId);
        }

    }


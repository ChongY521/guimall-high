package com.gulimall.order.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author aqiang9  2020-09-11
 */
@Controller
public class OrderWebController {

    @GetMapping("/list.html")
    public String list() {
        return "list";
    }

    @GetMapping("/detail.html")
    public String detail() {
        return "detail";
    }

    @GetMapping("/confirm.html")
    public String confirm() {
        return "confirm";
    }

    @GetMapping("/pay.html")
    public String pay() {
        return "pay";
    }
}

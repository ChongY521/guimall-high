package com.gulimall.cart.web;

import com.gulimall.cart.service.CartService;
import com.gulimall.cart.vo.CartItemVo;
import com.gulimall.cart.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author aqiang9  2020-09-08
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;

    /**
     * 浏览器有一个cookie: user-key ， 标识用户身份
     * 登陆 ： session 有
     * 没登陆 安装cookie 里面带来 user-key 来做
     * 第一次 如果没有零食用户 来创建  临时用户
     * 拦截器
     */
    @GetMapping("/list.html")
    public String list(Model model) {
//        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        CartVo cartVo = cartService.getCartList();
        model.addAttribute("cart", cartVo);
        return "list";
    }

    /**
     * 改变勾选状态
     * @param skuId
     * @param check
     * @return
     */
    @GetMapping("/changeCheck")
    public String changeCheck(@RequestParam("skuId")  Long skuId , @RequestParam("check") int check ){
        cartService.changeCheck(skuId , check ) ;
       return  "redirect:http://cart.gulimall.com/list.html";
    }

    /**
     * 改变商品数量
     * @param skuId
     * @param num
     * @return
     */
    @GetMapping("/changeNum")
    public String changeNum(@RequestParam("skuId")  Long skuId , @RequestParam("num") int num ){
        cartService.changeNum(skuId , num ) ;
        return  "redirect:http://cart.gulimall.com/list.html";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId")  Long skuId ){
        cartService.deleteItem(skuId ) ;
        return  "redirect:http://cart.gulimall.com/list.html";
    }

    /**
     * model.addFlashAttribute() 放在session 中 只能取一次
     * model.addAttribute 放在 url 后
     *
     * @param skuId
     * @param num
     * @return
     */

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes model) {
        cartService.addToCart(skuId, num);
        model.addAttribute("skuId", skuId);
        return "redirect:http://cart.gulimall.com/addToCartSuccess.html";
    }

    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {
        CartItemVo item = cartService.getCartItemInfo(skuId);
        model.addAttribute("item", item);
        return "success";
    }

}

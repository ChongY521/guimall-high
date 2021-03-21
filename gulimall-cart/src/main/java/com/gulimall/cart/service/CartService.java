package com.gulimall.cart.service;

import com.gulimall.cart.vo.CartItemVo;
import com.gulimall.cart.vo.CartVo;

/**
 * @author aqiang9  2020-09-08
 */
public interface CartService {
    CartItemVo addToCart(Long skuId, Integer num);

    CartItemVo getCartItemInfo(Long skuId);

    CartVo getCartList();
    /**
     * 清空购物车
     */
    void clearCart() ;


    /**
     * 改变选中状态
     * @param skuId  商品id
     * @param check 1 选中 0 不选中
     */
	void changeCheck(Long skuId, int check);

    void changeNum(Long skuId, int num);

    /**
     * 删除商品
     * @param skuId
     */
    void deleteItem(Long skuId);
}

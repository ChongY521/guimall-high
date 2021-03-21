package com.gulimall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车
 *
 * @author aqiang9  2020-09-08
 */
public class CartVo
{

	private List<CartItemVo> items;
	private Integer countNum; // 商品数量
	private Integer countType; // 商品类型数量
	private BigDecimal totalAmount; // 商品总价
	private BigDecimal reduce = BigDecimal.ZERO; // 减免价格

	public List<CartItemVo> getItems()
	{
		return items;
	}

	public void setItems(List<CartItemVo> items)
	{
		this.items = items;
	}

	public Integer getCountNum()
	{
		if (items != null && items.size() > 0)
		{
			return items.stream().map(CartItemVo::getCount).reduce(Integer::sum).get();
		}
		return 0;
	}

	public Integer getCountType()
	{
		return items == null ? 0 : items.size();
	}

	public BigDecimal getTotalAmount()
	{
		BigDecimal amount = BigDecimal.ZERO;

		if (items != null && items.size() > 0)
		{
			// 计算总额
			for (CartItemVo item : items)
			{
				if (item.getCheck())
				{
					amount = amount.add(item.getTotalPrice());
				}
			}
			amount = amount.subtract(getReduce());
		}

		return amount;
	}

	public BigDecimal getReduce()
	{
		return reduce;
	}

	public void setReduce(BigDecimal reduce)
	{
		this.reduce = reduce;
	}
}

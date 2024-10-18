package com.developer.orderservice.order.command.domain.repository;


import com.developer.orderservice.order.command.domain.aggregate.OrderGoods;

public interface OrderGoodsRepository {

    OrderGoods save(OrderGoods orderGoods);

}

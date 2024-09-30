package com.developer.order.command.domain.repository;

import com.developer.order.command.domain.aggregate.OrderGoods;

import java.util.List;

public interface OrderGoodsRepository {

    OrderGoods save(OrderGoods orderGoods);

}

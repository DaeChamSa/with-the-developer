package com.developer.order.command.domain.repository;

import com.developer.order.command.domain.aggregate.OrderGoods;

public interface OrderGoodsRepository {

    OrderGoods save(OrderGoods orderGoods);

}

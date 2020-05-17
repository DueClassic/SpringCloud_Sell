package com.duoclassic.order.service;

import com.duoclassic.order.dto.OrderDTO;

public interface OrderService {

    /**
     * 创建订单（只能买家操作）
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

    /**
     * 完结订单（只能卖家操作）
     * @param orderId
     * @return
     */
    OrderDTO finish(String orderId);
}

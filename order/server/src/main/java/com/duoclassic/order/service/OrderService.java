package com.duoclassic.order.service;

import com.duoclassic.order.dto.OrderDTO;

public interface OrderService {

    /**
     * 创建订单
     * @param orderDTO
     * @return
     */
    OrderDTO create(OrderDTO orderDTO);

}

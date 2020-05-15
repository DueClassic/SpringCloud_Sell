package com.duoclassic.product.service;

import com.duoclassic.product.common.DecreaseStockInput;
import com.duoclassic.product.common.ProductInfoOutput;
import com.duoclassic.product.dataobject.ProductInfo;

import java.util.List;

public interface ProductService {

    List<ProductInfo> findUpAll();

    List<ProductInfoOutput> findList(List<String> productIdList);

    void decreaseStock(List<DecreaseStockInput> decreaseStockInputList);
}

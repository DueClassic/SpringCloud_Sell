package com.duoclassic.product.service.impl;

import com.duoclassic.product.dataobject.ProductInfo;
import com.duoclassic.product.enums.ProductStatusEnum;
import com.duoclassic.product.repository.ProductCategoryRepository;
import com.duoclassic.product.repository.ProductInfoRepository;
import com.duoclassic.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }
}

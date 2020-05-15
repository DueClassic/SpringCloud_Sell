package com.duoclassic.product.service.impl;


import com.duoclassic.product.common.DecreaseStockInput;
import com.duoclassic.product.common.ProductInfoOutput;
import com.duoclassic.product.dataobject.ProductInfo;
import com.duoclassic.product.enums.ProductStatusEnum;
import com.duoclassic.product.enums.ResultEnum;
import com.duoclassic.product.exception.ProductException;
import com.duoclassic.product.repository.ProductInfoRepository;
import com.duoclassic.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfoOutput> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList).stream()
                .map(e -> {
                    ProductInfoOutput output = new ProductInfoOutput();
                    BeanUtils.copyProperties(e, output);
                    return output;
                })
                .collect(Collectors.toList());

    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> cartDTOList) {
        for (DecreaseStockInput cartDTO:cartDTOList){
            Optional<ProductInfo> productInfoOptional=productInfoRepository.findById(cartDTO.getProductId());
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            ProductInfo productInfo=productInfoOptional.get();
            Integer result=productInfo.getProductStock()-cartDTO.getProductQuantity();
            if (result<0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}

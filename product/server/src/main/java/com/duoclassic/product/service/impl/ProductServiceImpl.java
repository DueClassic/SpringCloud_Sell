package com.duoclassic.product.service.impl;


import com.duoclassic.product.common.DecreaseStockInput;
import com.duoclassic.product.common.ProductInfoOutput;
import com.duoclassic.product.dataobject.ProductInfo;
import com.duoclassic.product.enums.ProductStatusEnum;
import com.duoclassic.product.enums.ResultEnum;
import com.duoclassic.product.exception.ProductException;
import com.duoclassic.product.repository.ProductInfoRepository;
import com.duoclassic.product.service.ProductService;
import com.duoclassic.product.utils.JsonUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 查询所有上架商品
     * @return
     */
    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    /**
     * 根据商品id查找其商品信息
     * @param productIdList
     * @return
     */
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

    /**
     * 减库存
     * @param decreaseStockInputList
     */
    @Override
    public void decreaseStock(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList=decreaseStockProcess(decreaseStockInputList);
        List<ProductInfoOutput> productInfoOutputList=productInfoList.stream().map(e->{
            ProductInfoOutput output=new ProductInfoOutput();
            BeanUtils.copyProperties(e,output);
            return output;
        }).collect(Collectors.toList());
        amqpTemplate.convertAndSend("productInfo", JsonUtil.toJson(productInfoOutputList));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> decreaseStockInputList) {
        List<ProductInfo> productInfoList=new ArrayList<>();
        for (DecreaseStockInput cartDTO:decreaseStockInputList){
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
            productInfoList.add(productInfo);
        }
        return productInfoList;
    }
}

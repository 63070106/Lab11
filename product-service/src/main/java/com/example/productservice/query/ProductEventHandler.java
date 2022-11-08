package com.example.productservice.query;

import com.example.productservice.core.ProductEntity;
import com.example.productservice.core.data.ProductRepository;
import com.example.productservice.core.event.ProductCreatedEvent;
import com.sop.chapter9.core.event.ProductReservedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandler {

    private final ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        ProductEntity entity = new ProductEntity();
        BeanUtils.copyProperties(event, entity);
        productRepository.save(entity);
    }

    @EventHandler
    public void on(ProductReservedEvent productReservedEvent){
        ProductEntity productEntity = productRepository.findByProductId(productReservedEvent.getProductId());
        productEntity.setQuantity(productEntity.getQuantity() - productReservedEvent.getQuantity());
        productRepository.save(productEntity);
    }
}

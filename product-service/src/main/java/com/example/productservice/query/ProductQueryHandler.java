package com.example.productservice.query;

import com.example.productservice.core.ProductEntity;
import com.example.productservice.core.data.ProductRepository;
import com.example.productservice.query.rest.ProductQueryController;
import com.example.productservice.query.rest.ProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductQueryHandler {

    private final ProductRepository productRepository;

    public ProductQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    List<ProductRestModel> findProducts(FindProductQuery query){
        List<ProductRestModel> models = new ArrayList<>();
        List<ProductEntity> entities = productRepository.findAll();
        for (ProductEntity entity : entities){
            ProductRestModel model = new ProductRestModel();
            BeanUtils.copyProperties(entity, model);
            models.add(model);
        }

        return models;
    }
}

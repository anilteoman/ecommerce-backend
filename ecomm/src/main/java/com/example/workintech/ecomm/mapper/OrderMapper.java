package com.example.workintech.ecomm.mapper;


import com.example.workintech.ecomm.dto.OrderProductsResponse;
import com.example.workintech.ecomm.dto.OrderRequest;
import com.example.workintech.ecomm.dto.OrderResponse;
import com.example.workintech.ecomm.dto.ProductImagesResponse;
import com.example.workintech.ecomm.entity.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        List<OrderProductsResponse> orderProductsResponses = order.getProducts().stream().map(orderProduct -> {
            Product product = orderProduct.getProduct();
            List<ProductImagesResponse> images = product.getImages().stream().map(image -> new ProductImagesResponse(image.getUrl(), image.getIndex())).toList();

            return new OrderProductsResponse(
                    orderProduct.getId(),
                    orderProduct.getCount(),
                    orderProduct.getName(),
                    orderProduct.getDescription(),
                    product.getPrice(),
                    images);
        }).toList();

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getAddress().getId(),
                order.getCardNo(),
                order.getCardExpireMonth(),
                order.getCardExpireYear(),
                order.getCardName(),
                order.getPrice(),
                order.getOrderDate(),
                orderProductsResponses
        );
    };

    public Order toEntity(OrderRequest orderRequest, User user, Address address, List<Product> products) {
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setCardNo(orderRequest.card_no());
        order.setCardExpireMonth(orderRequest.card_expire_month());
        order.setCardExpireYear(orderRequest.card_expire_year());
        order.setCardName(orderRequest.card_name());
        order.setOrderDate(LocalDateTime.now());

        List<OrderProducts> orderProducts = orderRequest.products().stream().map(items -> {
            Product product = products.stream()
                    .filter(item -> item.getId().equals(items.product_id()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Product not found! " + items.product_id()));

            OrderProducts op = new OrderProducts();
            op.setProduct(product);
            op.setCount(items.count());
            op.setDescription(items.detail());
            op.setOrder(order);

            op.setName(product.getName());
            op.setImageUrl(product.getImages().get(0).getUrl());
            op.setPrice(product.getPrice());

            return op;
        }).collect(Collectors.toList());

        order.setProducts(orderProducts);

        double totalPrice = orderProducts.stream().mapToDouble(op -> op.getProduct().getPrice() * op.getCount()).sum();
        order.setPrice(totalPrice);

        return order;
    }
}

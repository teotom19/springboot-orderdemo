package eu.acme.demo.web.dto;

import eu.acme.demo.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class OrderDto extends OrderLiteDto {

    private List<OrderItemDto> orderItems;

    public OrderDto() {
    }

    public OrderDto(UUID id, OrderStatus status, String description, String clientReferenceCode, BigDecimal totalAmount, int itemCount, List<OrderItemDto> orderItems) {
        super(id, status, description, clientReferenceCode, totalAmount, itemCount);
        this.orderItems = orderItems;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
}

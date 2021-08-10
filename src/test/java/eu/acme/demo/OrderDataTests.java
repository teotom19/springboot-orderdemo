package eu.acme.demo;


import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.domain.enums.OrderStatus;
import eu.acme.demo.repository.OrderItemRepository;
import eu.acme.demo.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
public class OrderDataTests {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    public void testCreateOrder() {

        Order o = new Order();
        o.setStatus(OrderStatus.SUBMITTED);
        o.setClientReferenceCode("ORDER-1");
        o.setDescription("first order");
        o.setItemCount(10);
        o.setItemTotalAmount(BigDecimal.valueOf(100.23));
        orderRepository.save(o);

        Assert.isTrue(orderRepository.findById(o.getId()).isPresent(), "order not found");
        Assert.isTrue(!orderRepository.findById(UUID.randomUUID()).isPresent(), "non existing order found");

        OrderItem orderItem1 = new OrderItem(o, new BigDecimal(10), 10, new BigDecimal(100));
        OrderItem orderItem2 = new OrderItem(o, new BigDecimal(5), 10, new BigDecimal(50));

        orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2));

        List<OrderItem> orderItems = orderItemRepository
                .findAll()
                .stream()
                .filter(item -> item.getOrder().getId().equals(o.getId()))
                .collect(Collectors.toList());

        Assert.isTrue(orderItems.containsAll(Arrays.asList(orderItem1, orderItem2)), "Items not contained in retrieved list");

    }

}

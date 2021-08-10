package eu.acme.demo.domain;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import eu.acme.demo.repository.*;
import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.util.Arrays;

//mock data for orders and order items
@Configuration
public class LoadDatabase {

    private final OrderItemRepository orderItemRepository;

    public LoadDatabase(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Bean
    CommandLineRunner initOrders(OrderRepository orderRepository) {
        Order order1 = new Order("TOM19", "NO DESC", new BigDecimal(10), 10, OrderStatus.UNDER_PROCESS);
        Order order2 = new Order("TOM20", "NO DESC", new BigDecimal(30), 15, OrderStatus.SUBMITTED);
        Order order3 = new Order("DIM20", "NO DESC", new BigDecimal(30), 10, OrderStatus.PROCESSED);
        Order order4 = new Order("KAT15", "NO DESC", new BigDecimal(10), 5, OrderStatus.UNDER_PROCESS);
        OrderItem orderItem1 = new OrderItem(order1, new BigDecimal(1 ), 10, new BigDecimal(10));
        OrderItem orderItem2 = new OrderItem(order2, new BigDecimal(2), 10, new BigDecimal(20));
        OrderItem orderItem3 = new OrderItem(order3, new BigDecimal(3), 30, new BigDecimal(90));
        OrderItem orderItem4 = new OrderItem(order4, new BigDecimal(2), 15, new BigDecimal(30));

        return args -> {
            orderRepository.saveAll(Arrays.asList(order1, order2, order3, order4));
            orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2, orderItem3, orderItem4));
        };
    }
}



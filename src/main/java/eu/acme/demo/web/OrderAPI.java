package eu.acme.demo.web;

import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.domain.enums.OrderStatus;
import eu.acme.demo.domain.exceptions.OrderAlreadyExistsException;
import eu.acme.demo.domain.exceptions.OrderNotFoundException;
import eu.acme.demo.repository.OrderItemRepository;
import eu.acme.demo.repository.OrderRepository;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderItemDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequest;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@RestController
@RequestMapping("/orders")
public class OrderAPI {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;

    public OrderAPI(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<OrderLiteDto> fetchOrders() {

        List <OrderLiteDto> ordersList;
        ordersList = orderRepository.findAll().stream().map(order -> modelMapper.map(order, OrderLiteDto.class)).collect(Collectors.toList());

        if (ordersList.isEmpty()){
            System.out.println("No orders have been placed");
            return null;
        }else{
            return ordersList;
        }
    }

    @GetMapping("/{orderId}")
    public OrderDto fetchOrder(@PathVariable UUID orderId) throws OrderNotFoundException{

        Optional<Order> tempOrder = orderRepository.findById(orderId);

        if(tempOrder.isPresent()){
            System.out.println("Fetching order with id: " + orderId);
            return modelMapper.map(tempOrder,OrderDto.class);
        }else{
            throw new OrderNotFoundException(orderId);
        }
    }

    @PostMapping
    public OrderDto submitOrder(@RequestBody OrderRequest orderRequest) throws OrderAlreadyExistsException {

        Optional <Order> optionalOrder = orderRepository.findAll().stream().filter(order -> order.getClientReferenceCode().equals(orderRequest.getClientReferenceCode())).findFirst();

        if ((optionalOrder.isPresent())){

            throw new OrderAlreadyExistsException(orderRequest.getClientReferenceCode());

        }else{

            OrderDto tempOrder = orderRequest.getOrder();
            Order o = new Order();
            o.setStatus(tempOrder.getStatus());
            o.setClientReferenceCode(tempOrder.getClientReferenceCode());
            o.setDescription(tempOrder.getDescription());
            o.setItemCount(tempOrder.getItemCount());
            o.setItemTotalAmount(tempOrder.getTotalAmount());
            orderRepository.save(o);

            List<OrderItemDto> tempOrderItems = orderRequest.getOrder().getOrderItems();
            List <OrderItem> orderItems = new ArrayList<>();

            if(!(tempOrderItems.isEmpty())){
                for(OrderItemDto orderItemDto: tempOrderItems){
                    OrderItem tempItem = new OrderItem();
                    tempItem.setOrder(o);
                    tempItem.setTotalPrice(orderItemDto.getTotalPrice());
                    tempItem.setUnitPrice(orderItemDto.getUnitPrice());
                    tempItem.setUnits(orderItemDto.getUnits());
                    orderItems.add(tempItem);
                }
            }

            orderItemRepository.saveAll(orderItems);

            return orderRequest.getOrder();

        }
    }

}

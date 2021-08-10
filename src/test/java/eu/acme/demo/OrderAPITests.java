package eu.acme.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.acme.demo.domain.Order;
import eu.acme.demo.domain.OrderItem;
import eu.acme.demo.domain.enums.OrderStatus;
import eu.acme.demo.repository.OrderItemRepository;
import eu.acme.demo.repository.OrderRepository;
import eu.acme.demo.web.dto.OrderDto;
import eu.acme.demo.web.dto.OrderItemDto;
import eu.acme.demo.web.dto.OrderLiteDto;
import eu.acme.demo.web.dto.OrderRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderAPITests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private  OrderRepository orderRepository;

    @Test
    void testOrderAPI() throws Exception {

        OrderItemDto tempItem = new OrderItemDto(UUID.randomUUID(), 10, new BigDecimal(10), new BigDecimal(100));

        List<OrderItemDto> tempList = new ArrayList<>();

        tempList.add(tempItem);

        OrderDto tempOrder = new OrderDto(UUID.randomUUID(), OrderStatus.UNDER_PROCESS, "NO DESC"
                            ,"TOM27", new BigDecimal(20),  5, tempList);

        OrderRequest orderRequest = new OrderRequest("TOM27", tempOrder);

        String newOrderRequest = objectMapper.writeValueAsString(tempOrder);

        MvcResult orderResult = this.mockMvc.perform(post("http://localhost:8080/orders")
                        .content(newOrderRequest)
                        .contentType("application/json")
                        .accept("application/json"))
                        .andExpect(status().isOk())
                        .andReturn();

        OrderDto postedOrder = objectMapper.readValue(orderResult.getResponse().getContentAsString(), OrderDto.class);

        Assert.isTrue(tempOrder.equals(postedOrder), "Orders don't match" );
    }

    @Test
    void testOrderDoubleSubmission() throws Exception{

        Order tempOrder = new Order("TOM19", "NO DESC", new BigDecimal(10), 10, OrderStatus.UNDER_PROCESS);

        String newOrderRequest = objectMapper.writeValueAsString(tempOrder);

        //An order with "TOM19" as it's clientReferenceCode already exists in our database, so we only need to perform one post request
        MvcResult orderResult1 = this.mockMvc.perform(post("http://localhost:8080/orders")
                        .content(newOrderRequest)
                        .contentType("application/json")
                        .accept("application/json"))
                        .andExpect(status().isBadRequest())
                        .andReturn();
    }

    @Test
    void testFetchAllOrders() throws Exception{

        //4 orders are loaded into the database when LoadDatabase's CommandLiner runs +  1 order from testFetchCertainOrder()
        MvcResult orderResult = this.mockMvc.perform(MockMvcRequestBuilders.get("https://localhost:8080/orders")
                        .accept("application/json"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(5)))
                        .andReturn();

        System.out.println(orderResult.getResponse().getContentAsString());
    }

    @Test
    void testFetchCertainOrder() throws Exception{

        Order tempOrder = new Order("KAT25", "NO DESC", new BigDecimal(100), 10, OrderStatus.UNDER_PROCESS );
        orderRepository.save(tempOrder);

        MvcResult orderResult1 = this.mockMvc.perform(get("http://localhost:8080/orders/" + tempOrder.getId())
                        .accept("application/json"))
                        .andExpect(status().isOk())
                        .andReturn();

        MvcResult orderResult2 = this.mockMvc.perform(get("http://localhost:8080/orders/" + UUID.randomUUID())
                        .accept("application/json"))
                        .andExpect(status().isNotFound())
                        .andReturn();

    }
}


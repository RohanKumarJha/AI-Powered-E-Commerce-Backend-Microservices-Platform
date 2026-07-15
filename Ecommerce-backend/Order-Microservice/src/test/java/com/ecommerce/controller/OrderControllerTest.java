package com.ecommerce.controller;

import com.ecommerce.dto.request.OrderRequest;
import com.ecommerce.dto.request.UpdateOrderStatusRequest;
import com.ecommerce.dto.response.OrderItemResponse;
import com.ecommerce.dto.response.OrderResponse;
import com.ecommerce.dto.response.PageResponse;
import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.model.enums.PaymentMethod;
import com.ecommerce.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;


    @Test
    void createOrder() throws Exception {
        OrderRequest request = new OrderRequest();
        request.setAddressId(1L);
        request.setPaymentMethod(PaymentMethod.UPI);

        OrderItemResponse item = new OrderItemResponse();
        OrderResponse response = new OrderResponse();
        response.setUserId(1L);
        response.setOrderId(1L);
        response.setCartId(1L);
        response.setTotalAmount(BigDecimal.valueOf(2500));
        response.setOrderStatus(OrderStatus.PENDING);
        response.setPaymentMethod(PaymentMethod.UPI);
        response.setItems(List.of(item));
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(orderService.createOrder(any(OrderRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.cartId").value(1))
                .andExpect(jsonPath("$.totalAmount").value(2500))
                .andExpect(jsonPath("$.orderStatus").value("PENDING"))
                .andExpect(jsonPath("$.paymentMethod").value("UPI"))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void getOrderByIdTest() throws Exception {

        OrderItemResponse item = new OrderItemResponse();

        OrderResponse response = new OrderResponse();
        response.setUserId(1L);
        response.setOrderId(1L);
        response.setCartId(1L);
        response.setTotalAmount(BigDecimal.valueOf(2500));
        response.setOrderStatus(OrderStatus.PENDING);
        response.setPaymentMethod(PaymentMethod.UPI);
        response.setItems(List.of(item));
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(orderService.getOrderById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.cartId").value(1))
                .andExpect(jsonPath("$.totalAmount").value(2500))
                .andExpect(jsonPath("$.orderStatus").value("PENDING"))
                .andExpect(jsonPath("$.paymentMethod").value("UPI"))
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    void updateOrderStatusTest() throws Exception {
        UpdateOrderStatusRequest updateOrderStatusRequest = new UpdateOrderStatusRequest();
        updateOrderStatusRequest.setOrderStatus(OrderStatus.DELIVERED);

        OrderResponse response = new OrderResponse();
        response.setOrderId(1L);
        response.setUserId(1L);
        response.setCartId(1L);
        response.setTotalAmount(BigDecimal.valueOf(2500));
        response.setOrderStatus(OrderStatus.SHIPPED);
        response.setPaymentMethod(PaymentMethod.CREDIT_CARD);
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        when(orderService.updateOrderStatus(eq(1L), any(UpdateOrderStatusRequest.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/orders/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateOrderStatusRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.cartId").value(1))
                .andExpect(jsonPath("$.totalAmount").value(2500))
                .andExpect(jsonPath("$.orderStatus").value("SHIPPED"))
                .andExpect(jsonPath("$.paymentMethod").value("CREDIT_CARD"));

        verify(orderService).updateOrderStatus(eq(1L), any(UpdateOrderStatusRequest.class));
    }

    @Test
    void deleteOrderTest() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);
        mockMvc.perform(delete("/api/v1/orders/1"))
                .andExpect(status().isNoContent());
        verify(orderService, times(1)).deleteOrder(1L);
    }

    @Test
    void getOrdersByUserIdTest() throws Exception {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(1L);
        orderResponse.setUserId(1L);
        orderResponse.setCartId(1L);
        orderResponse.setTotalAmount(BigDecimal.valueOf(2500));
        orderResponse.setOrderStatus(OrderStatus.PENDING);
        orderResponse.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        PageResponse<OrderResponse> pageResponse = PageResponse.<OrderResponse>builder()
                .content(List.of(orderResponse))
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .first(true)
                .last(true)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(orderService.getOrdersByUserId(
                eq(1L),
                isNull(),
                isNull(),
                isNull(),
                isNull()))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/api/v1/orders/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].orderId").value(1))
                .andExpect(jsonPath("$.content[0].userId").value(1))
                .andExpect(jsonPath("$.content[0].totalAmount").value(2500))
                .andExpect(jsonPath("$.content[0].orderStatus").value("PENDING"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(orderService, times(1))
                .getOrdersByUserId(
                        eq(1L),
                        isNull(),
                        isNull(),
                        isNull(),
                        isNull());
    }

    @Test
    void getAllOrdersTest() throws Exception {

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderId(1L);
        orderResponse.setUserId(1L);
        orderResponse.setCartId(1L);
        orderResponse.setTotalAmount(BigDecimal.valueOf(2500));
        orderResponse.setOrderStatus(OrderStatus.PENDING);
        orderResponse.setPaymentMethod(PaymentMethod.CREDIT_CARD);

        PageResponse<OrderResponse> pageResponse = PageResponse.<OrderResponse>builder()
                .content(List.of(orderResponse))
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .first(true)
                .last(true)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(orderService.getAllOrders(
                isNull(),
                isNull(),
                isNull(),
                isNull()))
                .thenReturn(pageResponse);

        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].orderId").value(1))
                .andExpect(jsonPath("$.content[0].userId").value(1))
                .andExpect(jsonPath("$.content[0].cartId").value(1))
                .andExpect(jsonPath("$.content[0].totalAmount").value(2500))
                .andExpect(jsonPath("$.content[0].orderStatus").value("PENDING"))
                .andExpect(jsonPath("$.content[0].paymentMethod").value("CREDIT_CARD"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(true))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.hasPrevious").value(false));

        verify(orderService, times(1))
                .getAllOrders(
                        isNull(),
                        isNull(),
                        isNull(),
                        isNull());
    }
}

package com.kroon.its.order.service.service;

import com.kroon.its.order.service.dto.ChangeOrderStatusRequest;
import com.kroon.its.order.service.entity.Order;
import com.kroon.its.order.service.entity.OrderStatus;
import com.kroon.its.order.service.entity.OrderStatusHistory;
import com.kroon.its.order.service.exception.OrderNotFoundException;
import com.kroon.its.order.service.repository.OrderRepository;
import com.kroon.its.order.service.repository.OrderStatusHistoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;


    @Transactional
    public void changeStatus(@Valid ChangeOrderStatusRequest request
    ) {
        log.info("Change order status request: {}", request);

        Order order = findById(request.orderId());

        OrderStatus previousStatus = order.getStatus();

        if (previousStatus == request.newStatus()) {
            return;
        }
        order.setStatus(request.newStatus());

        orderRepository.save(order);

        OrderStatusHistory history = buildOrderStatusHistory
                (
                        order,
                        request,
                        previousStatus
                );

        orderStatusHistoryRepository.save(history);
    }

    private Order findById(UUID orderId
    ) {
        log.info("Find order by id {}", orderId);

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderNotFoundException(orderId));
    }

    private OrderStatusHistory buildOrderStatusHistory(Order order,
                                                       ChangeOrderStatusRequest request,
                                                       OrderStatus previousStatus
    ) {
        log.info("Build order status history for order id {}", order.getId());

        return OrderStatusHistory.builder()
                .orderId(order.getId())
                .previousStatus(previousStatus)
                .newStatus(request.newStatus())
                .source(request.source())
                .reason(request.reason())
                .correlationId(request.correlationId())
                .sagaId(request.sagaId())
                .changedBy(request.changedBy())
                .build();
    }
}


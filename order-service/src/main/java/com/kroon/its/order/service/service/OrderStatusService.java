package com.kroon.its.order.service.service;

import com.kroon.its.order.service.dto.ChangeOrderStatusRequest;
import com.kroon.its.order.service.entity.Order;
import com.kroon.its.order.service.entity.OrderStatus;
import com.kroon.its.order.service.entity.OrderStatusHistory;
import com.kroon.its.order.service.exception.OrderNotFoundException;
import com.kroon.its.order.service.repository.OrderRepository;
import com.kroon.its.order.service.repository.OrderStatusHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;



@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;


    @Transactional
    public void changeStatus(ChangeOrderStatusRequest request
    ) {
        log.info("Change order status request: {}", request);

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() ->
                        new OrderNotFoundException(request.orderId()));

        OrderStatus previousStatus = order.getStatus();

        if (previousStatus == request.newStatus()) {
            return;
        }
        order.setStatus(request.newStatus());

        orderRepository.save(order);

        var history = OrderStatusHistory.builder()
                .orderId(order.getId())
                .previousStatus(previousStatus)
                .newStatus(request.newStatus())
                .source(request.source())
                .reason(request.reason())
                .correlationId(request.correlationId())
                .sagaId(request.sagaId())
                .changedBy(request.changedBy())
                .build();
        orderStatusHistoryRepository.save(history);
    }
}


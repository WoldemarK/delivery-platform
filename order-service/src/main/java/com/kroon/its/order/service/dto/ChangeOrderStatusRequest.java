package com.kroon.its.order.service.dto;

import com.kroon.its.order.service.entity.OrderStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ChangeOrderStatusRequest
        (
                UUID orderId,
                OrderStatus newStatus,
                String source,
                String reason,
                UUID correlationId,
                UUID sagaId,
                String changedBy
        ) {
}

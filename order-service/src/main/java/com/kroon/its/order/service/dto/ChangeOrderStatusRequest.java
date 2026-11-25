package com.kroon.its.order.service.dto;

import com.kroon.its.order.service.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;


import java.util.UUID;

@Builder
public record ChangeOrderStatusRequest
        (
                @NotNull
                UUID orderId,

                @NotNull
                OrderStatus newStatus,

                @NotBlank
                @Size(max = 30)
                String source,

                @Size(max = 500)
                String reason,

                UUID correlationId,
                UUID sagaId,

                @Size(max = 100)
                String changedBy
        ) {
}

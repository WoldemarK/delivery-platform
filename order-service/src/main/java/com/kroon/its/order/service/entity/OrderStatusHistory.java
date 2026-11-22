package com.kroon.its.order.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_status_history")
public class OrderStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_status", length = 40)
    private OrderStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 40)
    private OrderStatus newStatus;


    @Column(
            name = "changed_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Instant changedAt;

    @Column(name = "changed_by", length = 100)
    private String changedBy;

    @Column(name = "source", nullable = false, length = 30)
    private String source;

    @Column(name = "reason", length = 500)
    private String reason;

    @Column(name = "correlation_id")
    private UUID correlationId;

    @Column(name = "saga_id")
    private UUID sagaId;

}

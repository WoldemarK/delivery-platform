package com.kroon.its.order.service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "delivery_address", nullable = false, length = 500)
    private String deliveryAddress;

    @Column(name = "delivery_city", nullable = false, length = 150)
    private String deliveryCity;

    @Column(name = "delivery_postal_code", length = 20)
    private String deliveryPostalCode;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "delivery_country_code", nullable = false, columnDefinition = "CHAR(2)")
    private String deliveryCountryCode;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "currency", nullable = false, columnDefinition = "CHAR(3)")
    private String currency;

    @Column(name = "eta_minutes")
    private Integer etaMinutes;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private OrderStatus status = OrderStatus.NEW;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @Builder.Default
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("position ASC")
    private List<OrderItem> items = new ArrayList<>();


    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();

        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = OrderStatus.NEW;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }
}

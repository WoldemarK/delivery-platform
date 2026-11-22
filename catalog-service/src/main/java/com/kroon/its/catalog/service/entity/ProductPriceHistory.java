package com.kroon.its.catalog.service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_price_history")
public class ProductPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "old_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal oldPrice;

    @Column(name = "new_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal newPrice;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(
            name = "currency",
            nullable = false,
            columnDefinition = "CHAR(3)",
            updatable = false,
            length = 3
    )
    private String currency;

    @Column(
            name = "changed_at",
            nullable = false,
            insertable = false,
            updatable = false
    )
    private Instant changedAt;

    @Column(name = "changed_by", length = 100)
    private String changedBy;
}

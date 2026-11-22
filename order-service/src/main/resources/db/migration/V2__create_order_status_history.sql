CREATE TABLE order_status_history
(
    id              UUID PRIMARY KEY,
    order_id        UUID        NOT NULL,
    previous_status VARCHAR(40),
    new_status      VARCHAR(40) NOT NULL,
    changed_at      TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    changed_by      VARCHAR(100),
    source          VARCHAR(30) NOT NULL,
    reason          VARCHAR(500),
    correlation_id  UUID,
    saga_id         UUID,

    CONSTRAINT fk_order_status_history_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
            ON DELETE RESTRICT,

    CONSTRAINT chk_order_status_history_previous_status
        CHECK (
            previous_status IS NULL
                OR previous_status IN (
                                       'NEW',
                                       'PROCESSING',
                                       'CONFIRMED',
                                       'IN_DELIVERY',
                                       'DELIVERY_FAILED',
                                       'RETURNING',
                                       'DELIVERED',
                                       'CANCEL_REQUESTED',
                                       'CANCELLED',
                                       'RETURNED'
                )
            ),

    CONSTRAINT chk_order_status_history_new_status
        CHECK (
            new_status IN (
                           'NEW',
                           'PROCESSING',
                           'CONFIRMED',
                           'IN_DELIVERY',
                           'DELIVERY_FAILED',
                           'RETURNING',
                           'DELIVERED',
                           'CANCEL_REQUESTED',
                           'CANCELLED',
                           'RETURNED'
                )
            ),

    CONSTRAINT chk_order_status_history_transition
        CHECK (
            previous_status IS NULL
                OR previous_status <> new_status
            )
);

CREATE INDEX idx_order_status_history_order_changed
    ON order_status_history (order_id, changed_at DESC, id DESC);
package com.kroon.its.order.service.entity;

public enum OrderStatusHistoryPreviousStatus {

    NEW,
    PROCESSING,
    CONFIRMED,
    IN_DELIVERY,
    DELIVERY_FAILED,
    RETURNING,
    DELIVERED,
    CANCEL_REQUESTED,
    CANCELLED,
    RETURNED
}

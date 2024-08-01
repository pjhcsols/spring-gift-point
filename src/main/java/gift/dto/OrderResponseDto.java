package gift.dto;

import gift.entity.Order;
import java.time.LocalDateTime;

public class OrderResponseDto {

    private final Long id;
    private final Long optionId;
    private final Long quantity;
    private final LocalDateTime orderDateTime;
    private final String message;

    public OrderResponseDto(Long id, Long optionId, Long quantity, LocalDateTime orderDateTime,
        String message) {
        this.id = id;
        this.optionId = optionId;
        this.quantity = quantity;
        this.orderDateTime = orderDateTime;
        this.message = message;
    }

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.optionId = order.getOption().getId();
        this.quantity = order.getQuantity();
        this.orderDateTime = order.getOrderDateTime();
        this.message = order.getMessage();
    }

    public Long getId() {
        return id;
    }

    public Long getOptionId() {
        return optionId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public String getMessage() {
        return message;
    }
}
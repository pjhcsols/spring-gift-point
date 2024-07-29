package gift.dto;

import java.time.LocalDateTime;

public class OrderResponseDto {

    private Long id;

    private Long optionId;

    private int quantity;

    private LocalDateTime orderDate;

    private String message;


    public OrderResponseDto() {
    }

    public OrderResponseDto(Long id, Long optionId, int quantity, LocalDateTime orderDate, String message) {
        this.id = id;
        this.optionId = optionId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

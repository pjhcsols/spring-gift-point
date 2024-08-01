package gift.dto;

import gift.exception.NoKakao;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductRequestDto {

    @NotEmpty(message = "상품 이름은 필수 입력 값입니다.")
    @Size(max = 15, message = "상품 이름은 공백 포함 15자 이하로 입력해야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣\\(\\)\\[\\]\\+\\-&/_ ]*$", message = "[ ] ( ), [ ], +, -, &, /, _ 의 특수문자만 사용가능합니다.")
    @NoKakao
    private String name;

    @Positive(message = "상품 가격은 양수여야 합니다.")
    private double price;

    @NotEmpty(message = "상품 url은 필수 입력 값입니다.")
    private String imageUrl;

    @NotNull(message = "카테고리는 하나 이상 지정되어야 합니다.")
    private Long categoryId;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String name, double price, String imageUrl, Long categoryId) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
package gift.domain.dto.request;

import gift.domain.annotation.RestrictedSpecialChars;
import gift.domain.entity.Product;
import gift.domain.service.CategoryService;
import gift.global.WebConfig.Constants.Domain;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductUpdateRequest(
    @NotNull
    @Size(
        min = Domain.Product.NAME_LENGTH_MIN,
        max = Domain.Product.NAME_LENGTH_MAX,
        message = Domain.Product.NAME_LENGTH_INVALID_MSG)
    @Pattern(regexp = "^(?!.*카카오).*$", message = Domain.Product.NAME_INCLUDE_KAKAO_MSG)
    @RestrictedSpecialChars
    String name,
    @NotNull
    Integer price,
    @NotNull
    String imageUrl,
    @NotNull
    Long categoryId
    ) {

    public static ProductUpdateRequest of(Product product) {
        return new ProductUpdateRequest(product.getName(), product.getPrice(), product.getImageUrl(), product.getCategory().getId());
    }

    public Product toEntity(CategoryService categoryService) {
        return new Product(name, price, imageUrl, categoryService.findById(categoryId));
    }
}

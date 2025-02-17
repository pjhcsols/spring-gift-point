package gift.controller.docs.user;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Member, Wishlist", description = "회원, 위시리스트 관련 API")
public interface UserApiDocs {
/*
    @Operation(summary = "회원 가입", security = {})
    ResponseEntity<AuthResponse> registerMember(
            RequestMemberDto requestMemberDto);

    @Operation(summary = "로그인", security = {})
    ResponseEntity<AuthResponse> loginMember(RequestMemberDto requestMemberDto);

    @Operation(summary = "토큰 재발급", security = {})
    ResponseEntity<String> reissueRefreshToken(String refreshToken);

    @Operation(summary = "위시리스트 페이지로 조회")
    ResponseEntity<ResponsePagingWishlistDto> getWishlistsByPage(
            @Parameter(hidden = true) Long memberId,
            Pageable pageable,
            @Parameter(hidden = true) Integer size);

    @Operation(summary = "위시리스트 추가")
    ResponseEntity<Long> addWishList(
            @Parameter(hidden = true) Long memberId,
            Long productId);

    @Operation(summary = "위시리스트 수정")
    ResponseEntity<Long> updateWishList(
            @Parameter(hidden = true) Long memberId,
            Long productId,
            RequestWishlistDto requestWishlistDto);

    @Operation(summary = "위시리스트 삭제")
    ResponseEntity<Void> deleteWishList(Long wishId);

 */
}

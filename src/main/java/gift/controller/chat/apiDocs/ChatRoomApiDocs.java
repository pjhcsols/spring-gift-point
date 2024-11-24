package gift.controller.chat.apiDocs;

import gift.domain.chat.ChatRoom;
import gift.global.ApiResponse.ApiResponse;
import gift.global.exception1.user.UserNotFoundException;
import gift.service.dto.chat.ChatRoomInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//@Tag(name = "작가 관련 기능", description = "채팅룸 관련 API, 채팅 조회 API를 제외한 모든 API는 인증 이후 사용할 수 있습니다.(Authorization: Bearer {token}이 필요합니다.)")

import java.util.List;

@Tag(name = "Chat Room API", description = "채팅룸 관련 기능 제공 API")
public interface ChatRoomApiDocs {

    @Operation(summary = "채팅룸 생성", description = "두 사용자를 기반으로 새로운 채팅룸 생성")
    @PostMapping
    ResponseEntity<ApiResponse<ChatRoom>> createChatRoom(
            @RequestParam String userEmail1,
            @RequestParam String userEmail2
    ) throws UserNotFoundException;

    @Operation(summary = "특정 채팅룸 조회", description = "채팅룸 ID를 기반으로 특정 채팅룸 조회")
    @GetMapping("/{chatRoomId}")
    ResponseEntity<ApiResponse<ChatRoom>> getChatRoom(@PathVariable Long chatRoomId);

    @Operation(summary = "채팅룸 삭제", description = "채팅룸 ID를 기반으로 특정 채팅룸 삭제")
    @DeleteMapping("/{chatRoomId}")
    ResponseEntity<ApiResponse<Void>> deleteChatRoom(@PathVariable Long chatRoomId);

    @Operation(summary = "유저의 채팅룸 목록 조회", description = "특정 유저가 속한 모든 채팅룸 조회")
    @GetMapping("/user/{userId}")
    ResponseEntity<ApiResponse<List<ChatRoomInfo>>> getUserChatRooms(@PathVariable Long userId);

    @Operation(summary = "채팅룸 제목 변경", description = "채팅룸 ID와 유저 정보를 기반으로 제목 변경")
    @PatchMapping("/{chatRoomId}/title")
    ResponseEntity<ApiResponse<Void>> setChatRoomTitle(
            @PathVariable Long chatRoomId,
            @RequestParam Long userId,
            @RequestParam String title
    );

    @Operation(summary = "모든 채팅룸 조회", description = "모든 채팅룸 목록 조회")
    @GetMapping
    ResponseEntity<ApiResponse<List<ChatRoom>>> getAllChatRooms();
}


package gift.controller.chat.apiDocs;

import gift.domain.chat.ChatMessage;
import gift.global.ApiResponse.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Chat Message API", description = "채팅 메시지 관련 기능 제공 API")
public interface ChatMessageApiDocs {

    @Operation(summary = "모든 채팅 메시지 조회", description = "저장된 모든 채팅 메시지를 반환합니다.")
    @GetMapping("/messages")
    ResponseEntity<ApiResponse<List<ChatMessage>>> getAllMessages();

    @Operation(summary = "특정 채팅방 메시지 조회", description = "채팅방 ID를 기반으로 메시지를 조회합니다.")
    @GetMapping("/rooms/{chatRoomId}/messages")
    ResponseEntity<ApiResponse<List<ChatMessage>>> getMessagesByChatRoom(@PathVariable Long chatRoomId);

    @Operation(summary = "이미지 URL을 Base64로 변환", description = "주어진 이미지 경로를 Base64로 변환합니다.")
    @PostMapping("/image/convert")
    ResponseEntity<ApiResponse<byte[]>> convertImageUrlToBase64(@RequestParam String imagePath) throws IOException;

    @Operation(summary = "채팅 메시지 삭제", description = "메시지 ID를 기반으로 특정 메시지를 삭제합니다.")
    @DeleteMapping("/messages/{id}")
    ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable Long id);
}

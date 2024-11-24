package gift.controller.chat;

import gift.controller.chat.apiDocs.ChatMessageApiDocs;
import gift.domain.chat.ChatMessage;
import gift.domain.chat.ChatRoom;
import gift.global.ApiResponse.ApiResponse;
import gift.global.ApiResponse.SuccessCode;
import gift.global.exception1.chat.ChatRoomIdNotFoundException;
import gift.service.chat.ChatMessageService;
import gift.service.chat.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat")
public class ChatMessageController implements ChatMessageApiDocs {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;

    /**
     * 모든 채팅 메시지 조회
     */
    @GetMapping("/messages")
    public ResponseEntity<ApiResponse<List<ChatMessage>>> getAllMessages() {
        List<ChatMessage> messages = chatMessageService.getAllMessages();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, messages));
    }

    /**
     * 특정 채팅방의 메시지 조회
     */
    @GetMapping("/rooms/{chatRoomId}/messages")
    public ResponseEntity<ApiResponse<List<ChatMessage>>> getMessagesByChatRoom(@PathVariable Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomIdNotFoundException(chatRoomId));

        List<ChatMessage> messages = chatMessageService.getMessagesByChatRoom(chatRoom);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, messages));
    }

    /**
     * 이미지 URL을 Base64로 변환
     */
    @PostMapping("/image/convert")
    public ResponseEntity<ApiResponse<byte[]>> convertImageUrlToBase64(@RequestParam String imagePath) throws IOException {
        byte[] imageBytes = chatMessageService.convertImageUrlToBytes(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, imageBytes));
    }

    /**
     * 메시지 삭제
     */
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable Long id) {
        chatMessageService.deleteMessage(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK));
    }
}

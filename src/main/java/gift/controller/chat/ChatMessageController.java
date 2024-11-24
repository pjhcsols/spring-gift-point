package gift.controller.chat;

import gift.domain.chat.ChatMessage;
import gift.domain.chat.ChatRoom;
import gift.global.exception1.chat.ChatRoomIdNotFoundException;
import gift.service.chat.ChatMessageService;
import gift.service.chat.ChatRoomService;
import gift.service.user.UserService;
import gift.util.ImageStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final ImageStorageUtil imageStorageUtil;

    // 모든 메시지 가져오기
    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return chatMessageService.getAllMessages();
    }

    // 특정 채팅방의 메시지를 시간 순서대로 반환
    @GetMapping("/rooms/{chatRoomId}/messages")
    public List<ChatMessage> getMessagesByChatRoom(@PathVariable Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomIdNotFoundException(chatRoomId));

        return chatMessageService.getMessagesByChatRoom(chatRoom);
    }

    @PostMapping("/image/convert")
    public ResponseEntity<byte[]> convertImageUrlToBase64(@RequestParam String imagePath) throws IOException {

        byte[] imageBytes = chatMessageService.convertImageUrlToBytes(imagePath);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @DeleteMapping("/messages/{id}")
    public void deleteMessage(@PathVariable Long id) {
        chatMessageService.deleteMessage(id);
    }
}
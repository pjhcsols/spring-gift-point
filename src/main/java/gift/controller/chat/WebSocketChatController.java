package gift.controller.chat;

import gift.domain.chat.ChatMessage;
import gift.domain.chat.ChatRoom;
import gift.domain.chat.MessageType;
import gift.global.exception1.user.UserNotFoundException;
import gift.service.chat.ChatMessageService;
import gift.service.chat.ChatRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
public class WebSocketChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final ChatRoomService chatRoomService;


    public WebSocketChatController(SimpMessageSendingOperations messagingTemplate,
                                   ChatMessageService chatMessageService,
                                   ChatRoomService chatRoomService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
        this.chatRoomService = chatRoomService;

    }

    @MessageMapping("/chat/{chatRoomId}")
    public void chat(@DestinationVariable Long chatRoomId, @RequestBody ChatMessageDto messageDto) {
        // 메시지를 서비스로 처리
        ChatMessage savedMessage = chatMessageService.saveMessage(chatRoomId, messageDto);

        // WebSocket을 통해 메시지를 전송
        messagingTemplate.convertAndSend("/v1/sub/chat/rooms/" + chatRoomId, savedMessage);
    }

    @MessageMapping("/chat/{chatRoomId}/file")
    @Operation(summary = "파일 전송", description = "WebSocket을 통해 특정 채팅방에 파일을 전송합니다.")
    public void sendFile(@DestinationVariable Long chatRoomId,
                         @RequestParam String fileBase64,
                         @RequestParam String userEmail) {
        try {
            // Base64 데이터를 이미지 파일로 변환 및 저장
            ChatMessage message = chatMessageService.saveFileMessage(chatRoomId, userEmail, fileBase64);

            // 이미지 바이트로 변환 후 Base64 인코딩
            byte[] imageBytes = chatMessageService.convertImageUrlToBytes(message.getContent());
            String encodedContent = ChatMessageDto.getImageContent(imageBytes);

            // 채팅 메시지 DTO 생성
            ChatMessageDto chatMessageDto = new ChatMessageDto(
                    message.getChatRoom().getId(),
                    message.getSender().getEmail(),
                    encodedContent,
                    message.getTimestamp().toString(),
                    MessageType.IMAGE
            );

            // WebSocket을 통해 채팅 메시지 전송
            messagingTemplate.convertAndSend("/api/sub/chat/rooms/" + chatRoomId, chatMessageDto);
        } catch (UserNotFoundException | IOException e) {
            // 에러 처리 및 로그
            log.error("파일 전송 중 오류 발생: " + e.getMessage(), e);
        }
    }

    @SubscribeMapping("/chat/rooms/{chatRoomId}/list")
    public List<ChatMessage> sendInitialMessages(@DestinationVariable Long chatRoomId) {
        // chatRoomId로 ChatRoom 객체를 조회
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);

        // 채팅방에 속한 메시지를 시간순으로 가져옴
        return chatMessageService.getMessagesByChatRoom(chatRoom);
    }


}
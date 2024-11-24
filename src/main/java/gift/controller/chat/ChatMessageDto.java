package gift.controller.chat;

import gift.domain.chat.MessageType;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private Long chatRoomId;
    private String sender;
    private String content;
    private String timestamp;
    private MessageType messageType;

    public ChatMessageDto() {
    }

    public ChatMessageDto(Long chatRoomId, String sender, String content, String timestamp, MessageType messageType) {
        this.chatRoomId = chatRoomId;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
        this.messageType = messageType;
    }

    public ChatMessageDto(String sender, String content, MessageType messageType) {
        this.sender = sender;
        this.content = content;
        this.messageType = messageType;
    }

    public static String getImageContent(byte[] contentBytes) {
        return "data:image/base64:," + java.util.Base64.getEncoder().encodeToString(contentBytes);
    }


}

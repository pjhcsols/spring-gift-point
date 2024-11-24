package gift.global.exception1.chat;

public class ChatRoomIdNotFoundException extends RuntimeException {
    public ChatRoomIdNotFoundException(Long chatRoomId) {
        super("Chat room with id " + chatRoomId + " not found.");
    }
}

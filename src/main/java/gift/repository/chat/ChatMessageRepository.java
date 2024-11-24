package gift.repository.chat;

import gift.domain.chat.ChatMessage;
import gift.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // 특정 채팅방의 메시지들을 시간순으로 정렬하여 반환하는 메서드
    List<ChatMessage> findByChatRoomOrderByTimestampAsc(ChatRoom chatRoom);

    // 특정 채팅방에 속한 메시지들을 삭제
    void deleteByChatRoom(ChatRoom chatRoom);
}

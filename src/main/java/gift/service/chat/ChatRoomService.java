package gift.service.chat;

import gift.domain.chat.ChatRoom;
import gift.domain.user.User;
import gift.global.exception1.chat.ChatRoomNotFoundException;
import gift.repository.chat.ChatMessageRepository;
import gift.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository; // 추가된 부분

    @Transactional
    public void deleteChatRoom(Long id) {
        ChatRoom chatRoom = chatRoomRepository.findById(id)
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat room not found with ID: " + id));

        chatMessageRepository.deleteByChatRoom(chatRoom);  // 채팅방에 속한 모든 메시지 삭제

        chatRoomRepository.delete(chatRoom);
    }

    public ChatRoom createChatRoom(User user1, User user2) {

        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findByUser1AndUser2(user1, user2);
        if (existingChatRoom.isPresent()) {
            return existingChatRoom.get();
        }

        ChatRoom chatRoom = new ChatRoom(user1, user2);
        return chatRoomRepository.save(chatRoom);
    }

    public Optional<ChatRoom> getChatRoom(User user1, User user2) {
        return Optional.ofNullable(chatRoomRepository.findByUser1AndUser2(user1, user2))
                .orElseThrow(() -> new ChatRoomNotFoundException("채팅방이 존재하지 않습니다."));
    }

    public Optional<ChatRoom> findById(Long id) {
        return chatRoomRepository.findById(id);
    }

    public ChatRoom getChatRoomById(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat room not found with ID: " + id));
    }

    public List<ChatRoom> getChatRoomsByUserId(Long userId) {
        return chatRoomRepository.findByUserId(userId);
    }

    @Transactional
    public void setChatRoomTitleIfUserMatches(Long chatRoomId, Long userId, String title) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("해당 ID의 채팅방을 찾을 수 없습니다: " + chatRoomId));

        if (chatRoom.getUser1().getId().equals(userId) || chatRoom.getUser2().getId().equals(userId)) {

            chatRoom.updateTitle(title);
        } else {
            throw new IllegalArgumentException("유저 ID가 채팅방 참가자와 일치하지 않습니다.");
        }
    }

    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }
}

package gift.service.chat;

import gift.controller.chat.ChatMessageDto;
import gift.domain.chat.ChatMessage;
import gift.domain.chat.ChatRoom;
import gift.domain.chat.MessageType;
import gift.domain.user.User;
import gift.global.exception1.user.UserNotFoundException;
import gift.repository.chat.ChatMessageRepository;
import gift.repository.chat.ChatRoomRepository;
import gift.repository.user.UserRepository;
import gift.service.user.UserService;
import gift.util.ImageStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ImageStorageUtil imageStorageUtil;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    @Transactional
    public ChatMessage saveMessage(Long chatRoomId, ChatMessageDto messageDto) {
        // 1. 채팅방을 찾음
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다."));

        // 2. 발신자 확인 (사용자 이메일을 통해)
        User sender = userRepository.findByEmail(messageDto.getSender())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 3. 메시지 객체 생성
        ChatMessage message = new ChatMessage(chatRoom, sender, messageDto.getContent(), messageDto.getMessageType());

        return chatMessageRepository.save(message);
    }

    @Transactional
    public ChatMessage saveFileMessage(Long chatRoomId, String userEmail, String fileBase64) throws IOException, UserNotFoundException {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        User sender = userService.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("발신자를 찾을 수 없습니다: " + userEmail));

        String folderPath = "chatRoom_" + chatRoom.getId();
        String imageUrl = imageStorageUtil.saveBase64Image(fileBase64, folderPath);  // 공통 메서드를 사용하여 저장

        ChatMessage message = new ChatMessage(chatRoom, sender, imageUrl, MessageType.IMAGE);

        return chatMessageRepository.save(message);
    }

    public byte[] convertImageUrlToBytes(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        return Files.readAllBytes(imageFile.toPath());
    }

    public String convertBytesToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public void deleteMessage(Long messageId) {
        chatMessageRepository.deleteById(messageId);
    }

    public List<ChatMessage> getMessagesByChatRoom(ChatRoom chatRoom) {
        return chatMessageRepository.findByChatRoomOrderByTimestampAsc(chatRoom);
    }

    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }

}

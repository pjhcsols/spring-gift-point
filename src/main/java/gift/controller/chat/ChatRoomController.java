package gift.controller.chat;

import gift.domain.chat.ChatRoom;
import gift.domain.chat.MessageType;
import gift.domain.user.User;
import gift.global.exception1.user.UserNotFoundException;
import gift.service.chat.ChatMessageService;
import gift.service.chat.ChatRoomService;
import gift.service.dto.chat.ChatRoomInfo;
import gift.service.user.UserService;
import gift.util.ImageStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chat/rooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final ImageStorageUtil imageStorageUtil;
    private final ChatMessageService chatMessageService;

    @PostMapping
    public ChatRoom createChatRoom(
            @RequestParam String userEmail1,
            @RequestParam String userEmail2
    ) throws UserNotFoundException {
        User user1 = userService.findByEmail(userEmail1)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userEmail1));
        User user2 = userService.findByEmail(userEmail2)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userEmail2));

        ChatRoom chatRoom = chatRoomService.createChatRoom(user1, user2);

        // 환영 메시지 생성
        String welcomeMessageContent = "Welcome, " + user2.getEmail() + "!";
        ChatMessageDto chatMessageDto = new ChatMessageDto(user1.getEmail(), welcomeMessageContent, MessageType.ENTER);
        chatMessageService.saveMessage(chatRoom.getId(), chatMessageDto);

        return chatRoom;
    }

    // 채팅방 번호로 연관 유저 조회 (GET /v1/chat/rooms/{chatRoomId})
    @GetMapping("/{chatRoomId}")
    public ChatRoom getChatRoom(@PathVariable Long chatRoomId) {
        return chatRoomService.getChatRoomById(chatRoomId);
    }

    // 채팅방 삭제 (DELETE /v1/chat/rooms/{chatRoomId})
    @DeleteMapping("/{chatRoomId}")
    public void deleteChatRoom(@PathVariable Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        // 채팅방 삭제 시 이미지 폴더도 삭제
        imageStorageUtil.deleteChatFolder("chatRoom_" + chatRoomId);
    }

    // 특정 유저의 채팅룸 ID와 제목을 가져오는 API (GET /v1/chat/rooms/user/{userId})
    @GetMapping("/user/{userId}")
    public List<ChatRoomInfo> getUserChatRooms(@PathVariable Long userId) {
        return chatRoomService.getChatRoomsByUserId(userId).stream()
                .map(chatRoom -> new ChatRoomInfo(chatRoom.getId(), chatRoom.getTitle()))
                .collect(Collectors.toList());
    }

    // 받은 채팅룸 ID와 해당되는 유저 ID가 일치한다면 제목을 설정하는 API (Patch /v1/chat/rooms/{chatRoomId}/title)
    @PatchMapping("/{chatRoomId}/title")
    public void setChatRoomTitle(@PathVariable Long chatRoomId,
                                 @RequestParam Long userId,
                                 @RequestParam String title) {

        chatRoomService.setChatRoomTitleIfUserMatches(chatRoomId, userId, title);
    }

    @GetMapping
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

}

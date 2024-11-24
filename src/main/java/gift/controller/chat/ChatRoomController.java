package gift.controller.chat;

import gift.controller.chat.apiDocs.ChatRoomApiDocs;
import gift.domain.chat.ChatRoom;
import gift.domain.chat.MessageType;
import gift.domain.user.User;
import gift.global.ApiResponse.ApiResponse;
import gift.global.ApiResponse.SuccessCode;
import gift.global.exception1.user.UserNotFoundException;
import gift.service.chat.ChatMessageService;
import gift.service.chat.ChatRoomService;
import gift.service.dto.chat.ChatRoomInfo;
import gift.service.user.UserService;
import gift.util.ImageStorageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/chat/rooms")
public class ChatRoomController implements ChatRoomApiDocs {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    private final ImageStorageUtil imageStorageUtil;
    private final ChatMessageService chatMessageService;

    /**
     * 새로운 채팅룸 생성
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ChatRoom>> createChatRoom(
            @RequestParam String userEmail1,
            @RequestParam String userEmail2
    ) throws UserNotFoundException {
        User user1 = userService.findByEmail(userEmail1)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userEmail1));
        User user2 = userService.findByEmail(userEmail2)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userEmail2));

        ChatRoom chatRoom = chatRoomService.createChatRoom(user1, user2);

        // Welcome message 추가
        String welcomeMessageContent = "Welcome, " + user2.getEmail() + "!";
        ChatMessageDto chatMessageDto = new ChatMessageDto(user1.getEmail(), welcomeMessageContent, MessageType.ENTER);
        chatMessageService.saveMessage(chatRoom.getId(), chatMessageDto);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, chatRoom));
    }

    /**
     * 특정 채팅룸 조회
     */
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponse<ChatRoom>> getChatRoom(@PathVariable Long chatRoomId) {
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, chatRoom));
    }

    /**
     * 채팅룸 삭제
     */
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponse<Void>> deleteChatRoom(@PathVariable Long chatRoomId) {
        chatRoomService.deleteChatRoom(chatRoomId);
        imageStorageUtil.deleteChatFolder("chatRoom_" + chatRoomId);

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK));
    }

    /**
     * 유저가 참여한 채팅룸 조회
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ChatRoomInfo>>> getUserChatRooms(@PathVariable Long userId) {
        List<ChatRoomInfo> chatRooms = chatRoomService.getChatRoomsByUserId(userId).stream()
                .map(chatRoom -> new ChatRoomInfo(chatRoom.getId(), chatRoom.getTitle()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, chatRooms));
    }

    /**
     * 채팅룸 제목 수정
     */
    @PatchMapping("/{chatRoomId}/title")
    public ResponseEntity<ApiResponse<Void>> setChatRoomTitle(
            @PathVariable Long chatRoomId,
            @RequestParam Long userId,
            @RequestParam String title
    ) {
        chatRoomService.setChatRoomTitleIfUserMatches(chatRoomId, userId, title);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK));
    }

    /**
     * 모든 채팅룸 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ChatRoom>>> getAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.OK, chatRooms));
    }
}

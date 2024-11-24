package gift.service.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomInfo {
    private Long id;
    private String title;

    public ChatRoomInfo(Long id, String title) {
        this.id = id;
        this.title = title;
    }

}
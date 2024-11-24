package gift.domain.chat;

import gift.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name = "chat_rooms")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "user1_id", nullable = false)
    private User user1;

    @ManyToOne
    //@JsonIgnore
    @JoinColumn(name = "user2_id", nullable = false)
    private User user2;

    private String title;

    protected ChatRoom() {}

    public ChatRoom(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}

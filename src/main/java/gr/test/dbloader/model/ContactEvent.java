package gr.test.dbloader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactEvent {
    private String phone;
    private String email;
    private String login;
    private String fio;
    private String inn;
    private String uid;
    private int source;
    private String comment;
    private java.sql.Timestamp eventDate;

    public static ContactEvent build() {
        return new ContactEvent(
                ThreadLocalRandom.current().nextLong(10000000000L, 10001000000L) + "",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                new Random().nextInt(100),
                UUID.randomUUID().toString(),
                new java.sql.Timestamp(System.currentTimeMillis())
        );
    }
}

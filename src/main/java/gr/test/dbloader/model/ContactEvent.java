package gr.test.dbloader.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class ContactEvent {
    private String phone;
    private String email;
    private String login;
    private String fio;
    private String inn;
    private String uid;
    private int source;
    private String comment;
    private Date date;

}

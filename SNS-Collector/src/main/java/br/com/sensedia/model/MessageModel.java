package br.com.sensedia.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class MessageModel {

    @Id
    @JsonIgnore
    private String id;

    @JsonAlias({"message", "Message"})
    private String message;

    @JsonAlias({"Timestamp"})
    private Date timestamp;
}

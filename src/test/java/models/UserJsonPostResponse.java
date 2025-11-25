package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserJsonPostResponse {
    private String id;
    private String name;
    private String job;
    private String createdAt;
}

package org.example.testing.model;/*
  @author   user
  @project   lab4
  @class  Entity
  @version  1.0.0 
  @since 07.03.2024 - 00.14
*/

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Entity")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class Entity {
    @Id
    String id;
    @NonNull
    String name;
    @NonNull
    String code;
    @NonNull
    String description;
//    LocalDateTime createdAt;
//    LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
                '}';
    }
}

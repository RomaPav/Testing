package org.example.testing.dto;/*
  @author   user
  @project   lab4
  @class  EntityDTO
  @version  1.0.0 
  @since 07.03.2024 - 00.20
*/

import lombok.*;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityDTO {
    String id;
    @NonNull
    String name;
    @NonNull
    String code;
    @NonNull
    String description;

}

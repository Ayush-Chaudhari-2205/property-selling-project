// UserStatusUpdateDTO.java
package com.propertyselling.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserStatusUpdateDTO {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Admin ID is required")
    private Long adminId;

    @NotNull(message = "Status must be specified")
    private Boolean isActive;
}

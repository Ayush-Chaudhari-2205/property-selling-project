package com.propertyselling.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SecurityQuestionDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Security question is required")
    private String question;

    @NotBlank(message = "Security answer is required")
    private String answer;
}

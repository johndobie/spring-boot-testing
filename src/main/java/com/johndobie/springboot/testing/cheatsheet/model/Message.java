package com.johndobie.springboot.testing.cheatsheet.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Validated
public class Message {

    @NotBlank(message = "Content is mandatory and cannot be blank")
    @NotNull(message = "Content is mandatory and cannot be null")
    @Length(max = 30, message = "length must be less than 30 characters")
    private String content;
}

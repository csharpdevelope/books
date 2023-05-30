package com.library.book.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String firstname;
    private String lastname;
}

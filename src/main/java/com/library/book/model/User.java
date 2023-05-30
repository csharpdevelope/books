package com.library.book.model;

import com.library.book.model.dto.UserDto;
import com.library.book.model.dto.UserListDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String token;

    public UserDto toDto() {
        UserDto userDto = new UserDto();
        userDto.setId(getId());
        userDto.setLastname(getLastname());
        userDto.setFirstname(getFirstname());
        userDto.setUsername(getUsername());
        userDto.setToken(getToken());
        return userDto;
    }
    public UserListDto toDtoList() {
        UserListDto userDto = new UserListDto();
        userDto.setId(getId());
        userDto.setLastname(getLastname());
        userDto.setFirstname(getFirstname());
        userDto.setUsername(getUsername());
        return userDto;
    }
}

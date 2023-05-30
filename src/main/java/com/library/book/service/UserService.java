package com.library.book.service;

import com.library.book.model.User;
import com.library.book.model.dto.request.LoginRequest;
import com.library.book.model.dto.request.UserRequest;
import com.library.book.model.dto.response.JSONResponse;

import java.util.List;

public interface UserService {

    Long count();

    JSONResponse saveUser(UserRequest request);

    JSONResponse login(LoginRequest loginRequest);

    List<User> findAllUser();

    String findUsername(String token);
}

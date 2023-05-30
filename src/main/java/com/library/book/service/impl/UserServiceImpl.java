package com.library.book.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.library.book.exception.BadRequestException;
import com.library.book.exception.NotFoundException;
import com.library.book.model.User;
import com.library.book.model.dto.request.LoginRequest;
import com.library.book.model.dto.request.UserRequest;
import com.library.book.model.dto.response.JSONResponse;
import com.library.book.repository.UserRepository;
import com.library.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Long count() {
        return userRepository.countAllUsers();
    }

    @Override
    public JSONResponse saveUser(UserRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("This username=" + request.getUsername() + " is already" );
        }
        ObjectNode response = objectMapper.createObjectNode();

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        userRepository.save(user);
        response.put("id", user.getId());
        response.put("username", user.getUsername());
        return new JSONResponse(response);
    }

    @Override
    public JSONResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new NotFoundException("Username or Password is not correct"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new NotFoundException("Username or Password is not correct");
        }

        String token = createToken();
        user.setActive(true);
        user.setToken(token);
        userRepository.save(user);
        return new JSONResponse(user.toDto());
    }

    @Override
    public List<User> findAllUser() {
        Page<User> users = userRepository.findAll(PageRequest.of(0, 10));
        return users.getContent();
    }

    @Override
    public String findUsername(String token) {
        Optional<User> user = userRepository.findByToken(token);
        return user.map(User::getUsername).orElse(null);
    }

    private String createToken() {
        return UUID.randomUUID().toString();
    }
}

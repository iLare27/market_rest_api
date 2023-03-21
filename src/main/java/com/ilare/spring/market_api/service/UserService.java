package com.ilare.spring.market_api.service;

import com.ilare.spring.market_api.dto.UserDTO;
import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.ForbiddenException;
import com.ilare.spring.market_api.exception.UserNotFoundException;
import com.ilare.spring.market_api.repository.UserRepository;
import com.ilare.spring.market_api.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void saveUser(User user, Principal principal) throws ForbiddenException {

        User principalUser = userRepository.findByEmail(principal.getName());
        user.setId(principalUser.getId());
        user.setRole(principalUser.getRole());

        if (!passwordEncoder.matches(user.getPassword(), principalUser.getPassword())) {
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }

        userRepository.save(user);
        }

    public UserDTO getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserDTO userDTO = UserDTO.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .build();
        return userDTO;
    }

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user: users) {
            UserDTO userDTO = UserDTO.builder()
                    .name(user.getName())
                    .nickname(user.getNickname())
                    .phoneNumber(user.getPhoneNumber())
                    .build();

            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

}

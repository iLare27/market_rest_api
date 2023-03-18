package com.ilare.spring.market_api.service;

import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.ForbiddenException;
import com.ilare.spring.market_api.exception.UserNotFoundException;
import com.ilare.spring.market_api.repository.UserRepository;
import com.ilare.spring.market_api.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void saveUser(User user, Principal principal) throws ForbiddenException { //TODO fix

        User principalUser = userRepository.findByEmail(principal.getName());

        if (user.getId() == principalUser.getId() || principalUser.getRole() == Role.ADMIN) {

            if (!passwordEncoder.matches(user.getPassword(), principalUser.getPassword())) {
                String password = passwordEncoder.encode(user.getPassword());
                user.setPassword(password);
            }
            userRepository.save(user);
        }
        else {
            throw new ForbiddenException("User must have admin permissions");
        }
    }

    public void deleteUser(Long id, Principal principal) throws UserNotFoundException, ForbiddenException {

        User principalUser = userRepository.findByEmail(principal.getName());

        if (principalUser.getRole() == Role.ADMIN) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            userRepository.delete(user);
        }
        else {
            throw new ForbiddenException("User must have admin permissions");
        }
    }

    public User getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return user;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

}

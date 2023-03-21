package com.ilare.spring.market_api.controller;

import com.ilare.spring.market_api.dto.UserDTO;
import com.ilare.spring.market_api.entity.User;
import com.ilare.spring.market_api.exception.ForbiddenException;
import com.ilare.spring.market_api.exception.UserNotFoundException;
import com.ilare.spring.market_api.repository.UserRepository;
import com.ilare.spring.market_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user, Principal principal) {
        try {
            userService.saveUser(user, principal);
            return ResponseEntity.ok(user);
        }
        catch(ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) {
        try {
            UserDTO userDTO = userService.getUserById(userId);
            return ResponseEntity.ok(userDTO);
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        String userEmail = principal.getName();
        User user = userRepository.findByEmail(userEmail);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOS = userService.getUsers();
        return ResponseEntity.ok(userDTOS);
    }

//    @DeleteMapping("/{userId}")
//    public ResponseEntity<String> deleteUser(@PathVariable Long userId, Principal principal) {
//        try {
//            userService.deleteUser(userId, principal);
//            return ResponseEntity.ok("User has been deleted");
//        }
//        catch(UserNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//        catch(ForbiddenException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user, Principal principal, BindingResult bindingResult) {
        try {
            userService.saveUser(user, principal);
            return ResponseEntity.ok(user);
        }
        catch(ForbiddenException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}


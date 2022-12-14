package org.alviel.user.web;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.alviel.user.domain.User;
import org.alviel.user.domain.UserCreateCommand;
import org.alviel.user.domain.UserId;
import org.alviel.user.domain.UserSearchCommand;
import org.alviel.user.domain.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    UserId create(@Valid @RequestBody CreateUserRequest cur) {
        UserCreateCommand command = UserCreateCommand.builder()
            .name(cur.getName())
            .email(cur.getEmail())
            .phone(cur.getPhone())
            .username(cur.getUsername())
            .build();
        return userService.create(command);
    }

    @GetMapping
    List<User> list() {
        return userService.all();
    }

    @GetMapping("/{userEmail}")
    List<User> getUserByEmail(@PathVariable String userEmail) {
        return userService.search(UserSearchCommand.ofEmail(userEmail));
    }

    @DeleteMapping("/{userId}")
    UserId delete(@PathVariable Integer userId) {
        return userService.delete(new UserId(userId));
    }
}

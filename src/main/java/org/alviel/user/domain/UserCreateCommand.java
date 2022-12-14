package org.alviel.user.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserCreateCommand {

    String name;
    String username;
    String email;
    String phone;

    public User toUserDomain() {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }
}

package org.alviel.user.domain;

import java.util.List;

public interface UserService {

    UserId create(UserCreateCommand ucc);

    List<User> all();

    List<User> search(UserSearchCommand usr);

    UserId delete(UserId userId);

}

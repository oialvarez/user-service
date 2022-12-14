package org.alviel.user.domain;

import java.util.List;
import javax.annotation.Resource;
import org.alviel.user.adapter.UserAdapter;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserAdapter userAdapter;

    @Override
    public UserId create(UserCreateCommand command) {
        List<User> byUsername = userAdapter.findByUsername(command.getUsername());
        if (!byUsername.isEmpty()) {
            throw new BadRequestException("User " + command.getUsername() + " already existing.");
        }

        List<User> byEmail = userAdapter.findByEmail(command.getEmail());
        if (!byEmail.isEmpty()) {
            throw new BadRequestException("User " + command.getEmail() + " already existing.");
        }

        User newUser = userAdapter.save(command.toUserDomain());
        return new UserId(newUser.getId());
    }

    @Override
    public List<User> all() {
        return (List<User>) userAdapter.findAll();
    }

    @Override
    public List<User> search(UserSearchCommand usr) {
        return userAdapter.findByEmail(usr.getEmail());
    }

    @Override
    public UserId delete(UserId userId) {
        if(!userAdapter.existsById(userId.getId())) {
            throw new BadRequestException("User does not exists.");
        }
        userAdapter.deleteById(userId.getId());
        return userId;
    }
}

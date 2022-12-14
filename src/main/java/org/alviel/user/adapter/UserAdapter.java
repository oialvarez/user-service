package org.alviel.user.adapter;

import java.util.List;
import javax.annotation.Resource;
import org.alviel.user.domain.User;
import org.springframework.data.repository.CrudRepository;

@Resource
public interface UserAdapter extends CrudRepository<User, Integer> {

    List<User> findByUsername(String username);

    List<User> findByEmail(String email);

}

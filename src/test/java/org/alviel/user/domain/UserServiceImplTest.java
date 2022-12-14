package org.alviel.user.domain;

import java.util.List;
import lombok.SneakyThrows;
import org.alviel.user.adapter.UserAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserAdapter userAdapter;

    @InjectMocks
    private UserServiceImpl userService;

    private AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        openMocks.close();
    }

    @Test
    void givenCreateUser_whenUserByEmailFound_shouldFail() {
        givenUserByEmailFound();

        String message = assertThrows(BadRequestException.class, this::whenCreatingUser)
            .getMessage();

        assertThat(message).isEqualTo("400 BAD_REQUEST \"User lolo@lala.cl already existing.\"");
    }

    @Test
    void givenCreateUser_whenUserByUsernameFound_shouldFail() {
        givenUserByUsernameFound();

        String message = assertThrows(BadRequestException.class, this::whenCreatingUser)
            .getMessage();

        assertThat(message).isEqualTo("400 BAD_REQUEST \"User trololo already existing.\"");
    }

    @Test
    void givenCreateUser_whenCannotCreateUser_shouldSucceed() {
        when(userAdapter.save(any())).thenReturn(givenUser());

        assertDoesNotThrow(this::whenCreatingUser);
    }

    @Test
    void givenDeleteUser_whenUserDoesNotExists_shouldFail() {
        givenUserExisting(false);

        String message = assertThrows(BadRequestException.class, this::whenDeletingUser)
            .getMessage();

        assertThat(message).isEqualTo("400 BAD_REQUEST \"User does not exists.\"");
    }

    @Test
    void givenDeleteUser_whenExists_shouldFail() {
        givenUserExisting(true);
        givenUserDeleted();

        assertDoesNotThrow(this::whenDeletingUser);
    }

    private void givenUserDeleted() {
        doNothing().when(userAdapter).deleteById(anyInt());
    }

    private void givenUserExisting(boolean value) {
        when(userAdapter.existsById(anyInt())).thenReturn(value);
    }

    private void givenUserByEmailFound() {
        when(userAdapter.findByEmail(anyString()))
            .thenReturn(List.of(givenUser()));
    }

    private void givenUserByUsernameFound() {
        when(userAdapter.findByUsername(anyString()))
            .thenReturn(List.of(givenUser()));
    }

    private User givenUser() {
        User u = new User();
        u.setId(1);
        return u;
    }

    private void whenCreatingUser() {
        userService.create(UserCreateCommand.builder()
            .email("lolo@lala.cl")
            .username("trololo")
            .build());
    }

    private void whenDeletingUser() {
        userService.delete(new UserId(1));
    }

}
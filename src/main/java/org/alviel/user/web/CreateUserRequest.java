package org.alviel.user.web;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Size(min = 3, max = 30, message = "{user.name.invalid}")
    @NotEmpty(message = "Please enter name")
    private String name;

    @Size(min = 4, max = 25, message = "{user.username.invalid}")
    @NotEmpty(message = "Please enter username")
    private String username;
    @Email(message = "{user.email.invalid}")
    @NotEmpty(message = "Please enter email")
    private String email;
    @Size(min = 6, max = 20, message = "{user.phone.invalid}")
    @NotEmpty(message = "Please enter phone")
    private String phone;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


    public String getPhone() {
        return phone;
    }

}

package org.alviel.user.domain;

public class UserSearchCommand {

    private String email;

    public static UserSearchCommand ofEmail(String userEmail) {
        UserSearchCommand usr = new UserSearchCommand();
        usr.setEmail(userEmail);
        return usr;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

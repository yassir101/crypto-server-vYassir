package org.cryptoserver.users;

import org.cryptoserver.users.components.UserDetails;

public class User {

    private UserDetails details;

    public User(UserDetails details) {
        this.details = details;
    }

    public UserDetails getDetails() {
        return this.details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }
}

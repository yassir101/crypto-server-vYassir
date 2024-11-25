package org.cryptoserver.users.components;

public class UserDetails {
    private final int id;
    private String username;
    private String email;

    public UserDetails(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package org.cryptoserver.storage.dao;

import org.cryptoserver.storage.Database;
import org.cryptoserver.users.components.UserDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDetailsDao {

    /**
     * Returns the UserDetails instance of the searched user.
     * If no user is found, then returns null.
     *
     * @param username username of the searched user
     * @param password password of the searched user
     * @return instance of UserDetails
     */
    public UserDetails get(String username, String password) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        UserDetails userDetails = null;

        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                userDetails = new UserDetails(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("email"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userDetails;
    }

    /**
     * Update user details table with specified user details object given
     * @param userDetails
     */
    public void save(UserDetails userDetails) {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        String sql = "UPDATE users SET username = ?, email = ? WHERE id = ?";

        try {
            connection = Database.getInstance().getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userDetails.getUsername());
            preparedStatement.setString(2, userDetails.getEmail());
            preparedStatement.setInt(3, userDetails.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

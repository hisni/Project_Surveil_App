package com.aslam.co321_project.data;

import android.widget.Toast;

import com.aslam.co321_project.data.model.LoggedInUser;
import com.aslam.co321_project.ui.login.LoginActivity;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication



            if (username.equals("aslam@gmail.com") && password.equals("aslamm")){
                LoggedInUser user =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                username);
                return new Result.Success<>(user);
            } else {
                return null;
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}

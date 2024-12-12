package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.shared.dto.*;
import com.socialnetwork.connecthub.shared.exceptions.*;

import java.util.List;

public interface UserAccountService {
    void signup(SignUpDTO signUpDTO) throws InvalidSignupException;
    UserDTO login(LoginDTO loginDTO) throws InvalidLoginException ;
    void logout(String userId);
    String hashPassword(String password);
    void updateUserStatus(String userId, boolean onlineStatus);
    UserDTO getUserById(String userId);
    List<UserDTO> getUsersByUsername(String userId, String username);
}
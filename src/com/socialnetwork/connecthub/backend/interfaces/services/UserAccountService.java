package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.shared.dto.LoginDTO;
import com.socialnetwork.connecthub.shared.dto.SignUpDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public interface UserAccountService {
    void signup(SignUpDTO signUpDTO);
    boolean login(LoginDTO loginDTO);
    void logout(String userId);
    String hashPassword(String password);
    void updateUserStatus(String userId, boolean onlineStatus);
    UserDTO getUserById(String userId);
}
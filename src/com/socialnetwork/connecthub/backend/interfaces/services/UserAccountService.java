package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.shared.dto.*;
import com.socialnetwork.connecthub.shared.exceptions.*;

public interface UserAccountService {
    void signup(SignUpDTO signUpDTO) throws InvalidSignupException;
    boolean login(LoginDTO loginDTO) throws InvalidLoginException ;
    void logout(String userId);
    String hashPassword(String password);
    void updateUserStatus(String userId, boolean onlineStatus);
    UserDTO getUserById(String userId);
}
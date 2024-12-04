package java.com.socialnetwork.connecthub.backend.interfaces.services;

import java.com.socialnetwork.connecthub.shared.dto.*;

public interface UserAccountService {
    void signup(SignUpDTO signUpDTO);
    boolean login(LoginDTO loginDTO);
    void logout(String userId);
    String hashPassword(String password);
    void updateUserStatus(String userId, boolean onlineStatus);
    UserDTO getUserById(String userId);
}
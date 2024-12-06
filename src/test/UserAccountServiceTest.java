package test;

import com.socialnetwork.connecthub.backend.interfaces.services.UserAccountService;
import com.socialnetwork.connecthub.shared.dto.LoginDTO;
import com.socialnetwork.connecthub.shared.dto.SignUpDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public class UserAccountServiceTest implements UserAccountService {
    @Override
    public void signup(SignUpDTO signUpDTO) {

    }

    @Override
    public UserDTO login(LoginDTO loginDTO) {
        return null;
    }

    @Override
    public void logout(String userId) {

    }

    @Override
    public String hashPassword(String password) {
        return "";
    }

    @Override
    public void updateUserStatus(String userId, boolean onlineStatus) {

    }

    @Override
    public UserDTO getUserById(String userId) {
        UserDTO user = new UserDTO();
        user.setUserId(userId);
        user.setProfilePhotoPath("src/test/Screenshot 2024-12-03 011157.png");
        user.setUsername("User " + userId);
        return user;
    }
}

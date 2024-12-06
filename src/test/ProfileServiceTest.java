package test;

import com.socialnetwork.connecthub.backend.interfaces.services.ProfileService;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.List;

public class ProfileServiceTest implements ProfileService {
    @Override
    public void updateProfilePhoto(String userId, String photoPath) {

    }

    @Override
    public void updateCoverPhoto(String userId, String photoPath) {

    }

    @Override
    public void updateBio(String userId, String bio) {

    }

    @Override
    public void updatePassword(String userId, String newPassword) {

    }

    @Override
    public List<UserDTO> getUserFriends(String userId) {
        List<UserDTO> friends = new java.util.ArrayList<>();
        for (int i = 0; i < 30; i++) {
            UserDTO user = new UserDTO();
            user.setUserId(String.valueOf(i));
            user.setUsername("User " + i);
            user.setProfilePhotoPath("src/test/Screenshot 2024-12-03 011157.png");
            friends.add(user);
        }
        return friends;
    }
}

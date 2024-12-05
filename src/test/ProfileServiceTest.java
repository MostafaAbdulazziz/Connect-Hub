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
        return List.of();
    }
}

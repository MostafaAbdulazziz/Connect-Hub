package java.com.socialnetwork.connecthub.shared.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String userId;
    private String username;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private boolean onlineStatus;
}
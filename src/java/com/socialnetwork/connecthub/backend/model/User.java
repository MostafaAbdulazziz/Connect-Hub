package java.com.socialnetwork.connecthub.backend.model;

import lombok.Data;
import java.util.Date;
import java.util.List;


@Data
public class User {
    private String userId;
    private String email;
    private String username;
    private String hashedPassword;
    private Date dateOfBirth;
    private boolean onlineStatus;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private List<String> friends;
    private List<String> blockedUsers;
    private List<String> receivedFriendRequests;
}
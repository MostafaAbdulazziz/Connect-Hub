package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.frontend.swing.view.View;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public interface NavigationHandler {
    View goToLoginView();
    View goToSignUpView();
    View goToBlockedUsersView(UserDTO userDTO);
    View goToContentCreationAreaView(UserDTO userDTO);
    View goToFriendRequestView(UserDTO userDTO);
    View goToMyProfileView(UserDTO userDTO);
    View goToNewsFeedView(UserDTO userDTO);
    View goToProfileView(UserDTO userDTO);
    View goToStoryView(UserDTO userDTO);

}

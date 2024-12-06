package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public interface NavigationHandler {
    LoginView goToLoginView();
    SignUpView goToSignUpView();
    ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO);
    ManageFriendsView goToManageFriendsView(UserDTO userDTO);
    MyProfileView goToMyProfileView(UserDTO userDTO);
    NewsFeedView goToNewsFeedView(UserDTO userDTO);
    ProfileView goToProfileView(UserDTO userDTO);
    StoryView goToStoryView(ContentDTO userDTO);
    EditMyProfileView goToEditMyProfileView(UserDTO userDTO);

}

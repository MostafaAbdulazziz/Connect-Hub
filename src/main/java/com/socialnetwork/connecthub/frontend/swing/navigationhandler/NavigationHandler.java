package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;

public interface NavigationHandler {
    LoginView goToLoginView();
    SignUpView goToSignUpView();
    ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO, boolean isPost);
    ManageFriendsView goToManageFriendsView(UserDTO userDTO, JFrame parentFrame);
    MyProfileView goToMyProfileView(UserDTO userDTO);
    NewsFeedView goToNewsFeedView(UserDTO userDTO);
    ProfileView goToProfileView(UserDTO anotherUserDTO, UserDTO userDTO);
    StoryView goToStoryView(ContentDTO userDTO);
    EditMyProfileView goToEditMyProfileView(UserDTO userDTO);

    GroupCreationView goToGroupCreationView(UserDTO user);
}

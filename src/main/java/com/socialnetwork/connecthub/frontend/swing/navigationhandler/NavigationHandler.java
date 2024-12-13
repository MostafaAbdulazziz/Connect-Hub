package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;

public interface NavigationHandler {
    LoginView goToLoginView();
    SignUpView goToSignUpView();
    ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO, boolean isPost);
    ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO,GroupDTO groupDTO);
    ManageFriendsView goToManageFriendsView(UserDTO userDTO, JFrame parentFrame);
    MyProfileView goToMyProfileView(UserDTO userDTO);
    NewsFeedView goToNewsFeedView(UserDTO userDTO);
    ProfileView goToProfileView(UserDTO anotherUserDTO, UserDTO userDTO);
    StoryView goToStoryView(ContentDTO userDTO);
    EditMyProfileView goToEditMyProfileView(UserDTO userDTO);
    GroupCreationView goToGroupCreationView(UserDTO user);
    MyGroupsView goToMyGroupsView(UserDTO userDTO);
    GroupView goToGroupView(GroupDTO groupDTO, UserDTO userDTO);
    SearchResultsView goToSearchResultsView(UserDTO user, String query, JFrame parentFrame);
    ManageJoinRequestsView goToJoinRequestsView( GroupDTO group);
    ManageAdminsView goToManageAdminsView(GroupDTO group);
    NotificationView goToNotificationView(UserDTO userDTO);
    PostView goToPostView(ContentDTO contentDTO);
}

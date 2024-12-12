package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;

public class TestNavigationHandler implements NavigationHandler {

    @Override
    public LoginView goToLoginView() {
        System.out.println("new LoginView()");
        return null;
    }

    @Override
    public SignUpView goToSignUpView() {
        System.out.println("new SignUpView()");
        return null;

    }

    @Override
    public ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO, boolean isPost) {
        System.out.println("new ContentCreationAreaView()");
        return null;

    }

    @Override
    public ManageFriendsView goToManageFriendsView(UserDTO userDTO, JFrame parentFrame) {
        System.out.println("new ManageFriendsView()");
        return null;

    }

    @Override
    public MyProfileView goToMyProfileView(UserDTO userDTO) {
        System.out.println("new MyProfileView()");
        return null;

    }

    @Override
    public NewsFeedView goToNewsFeedView(UserDTO userDTO) {
        System.out.println("new NewsFeedView()");
        return null;

    }

    @Override
    public ProfileView goToProfileView(UserDTO anotherUserDTO, UserDTO userDTO) {
        System.out.println("new ProfileView()");
        return null;

    }

    @Override
    public StoryView goToStoryView(ContentDTO contentDTO) {
        System.out.println("new StoryView()");
        return null;
    }

    @Override
    public EditMyProfileView goToEditMyProfileView(UserDTO userDTO) {
        System.out.println("new EditMyProfileView()");
        return null;
    }





    @Override
    public MyGroupsView goToMyGroupsView(UserDTO userDTO) {
        return null;
    }

    @Override
    public GroupView goToGroupView(GroupDTO groupDTO, UserDTO userDTO) {
        return null;
    }

}

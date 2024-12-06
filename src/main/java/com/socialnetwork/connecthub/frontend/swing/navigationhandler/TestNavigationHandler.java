package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

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
    public ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO) {
        System.out.println("new ContentCreationAreaView()");
        return null;

    }

    @Override
    public ManageFriendsView goToManageFriendsView(UserDTO userDTO) {
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
    public ProfileView goToProfileView(UserDTO userDTO) {
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

}

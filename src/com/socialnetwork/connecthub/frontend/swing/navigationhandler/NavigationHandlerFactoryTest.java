package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public class NavigationHandlerFactoryTest implements NavigationHandler {

    @Override
    public View goToLoginView() {
        System.out.println("new LoginView()");
        return null;
    }

    @Override
    public View goToSignUpView() {
        System.out.println("new SignUpView()");
        return null;

    }

    @Override
    public View goToBlockedUsersView(UserDTO userDTO) {
        System.out.println("new BlockedUsersView()");
        return null;

    }

    @Override
    public View goToContentCreationAreaView(UserDTO userDTO) {
        System.out.println("new ContentCreationAreaView()");
        return null;

    }

    @Override
    public View goToFriendRequestView(UserDTO userDTO) {
        System.out.println("new FriendRequestView()");
        return null;

    }

    @Override
    public View goToMyProfileView(UserDTO userDTO) {
        System.out.println("new MyProfileView()");
        return null;

    }

    @Override
    public View goToNewsFeedView(UserDTO userDTO) {
        System.out.println("new NewsFeedView()");
        return null;

    }

    @Override
    public View goToProfileView(UserDTO userDTO) {
        System.out.println("new ProfileView()");
        return null;

    }

    @Override
    public View goToStoryView(UserDTO userDTO) {
        System.out.println("new StoryView()");
        return null;
    }

}

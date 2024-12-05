package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.frontend.swing.navigationhandler.interfaces.NavigationHandler;
import com.socialnetwork.connecthub.frontend.swing.view.*;

public class NavigationHandlerTest implements NavigationHandler {

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
    public View goToBlockedUsersView() {
        System.out.println("new BlockedUsersView()");
        return null;

    }

    @Override
    public View goToContentCreationAreaView() {
        System.out.println("new ContentCreationAreaView()");
        return null;

    }

    @Override
    public View goToFriendRequestView() {
        System.out.println("new FriendRequestView()");
        return null;

    }

    @Override
    public View goToMyProfileView() {
        System.out.println("new MyProfileView()");
        return null;

    }

    @Override
    public View goToNewsFeedView() {
        System.out.println("new NewsFeedView()");
        return null;

    }

    @Override
    public View goToProfileView() {
        System.out.println("new ProfileView()");
        return null;

    }

    @Override
    public View goToStoryView() {
        System.out.println("new StoryView()");
        return null;
    }

}

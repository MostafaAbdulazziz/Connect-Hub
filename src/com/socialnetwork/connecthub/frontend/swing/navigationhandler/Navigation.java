package com.socialnetwork.connecthub.frontend.swing.navigationhandler;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.interfaces.NavigationHandler;
import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.frontend.swing.viewfactory.ViewFactory;

public class Navigation implements NavigationHandler {
    ViewFactory viewFactory = new ViewFactory();
    @Override
    public View goToLoginView() {
        return viewFactory.getView("LoginView");
    }

    @Override
    public View goToSignUpView() {
        return viewFactory.getView("SignUpView");
    }

    @Override
    public View goToBlockedUsersView() {
        return viewFactory.getView("BlockedUsersView");
    }

    @Override
    public View goToContentCreationAreaView() {
        return viewFactory.getView("ContentCreationAreaView");
    }

    @Override
    public View goToFriendRequestView() {
        return viewFactory.getView("FriendRequestView");
    }

    @Override
    public View goToMyProfileView() {
        return viewFactory.getView("MyProfileView");
    }

    @Override
    public View goToNewsFeedView() {
        return viewFactory.getView("NewsFeedView");
    }

    @Override
    public View goToProfileView() {
        return viewFactory.getView("ProfileView");
    }

    @Override
    public View goToStoryView() {
        return viewFactory.getView("StoryView");
    }

}

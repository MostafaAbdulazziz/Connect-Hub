package com.socialnetwork.connecthub.frontend.swing.viewfactory;

import com.socialnetwork.connecthub.frontend.swing.view.LoginView;
import com.socialnetwork.connecthub.frontend.swing.view.SignUpView;
import com.socialnetwork.connecthub.frontend.swing.view.*;

public class ViewFactory {
    public View getView(String viewName) {
        return switch (viewName) {
            case "LoginView" -> new LoginView();
            case "SignUpView" -> new SignUpView();
            case "BlockedUsersView" -> new BlockedUsersView();
            case "ContentCreationAreaView" -> new ContentCreationAreaView();
            case "FriendRequestView" -> new FriendRequestsView();
            case "MyProfileView" -> new MyProfileView();
            case "NewsFeedView" -> new NewsFeedView();
            case "ProfileView" -> new ProfileView();
            case "StoryView" -> new StoryView();
            default -> null;
        };
    }
}

package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.frontend.swing.view.View;

public interface NavigationHandler {
    View goToLoginView();
    View goToSignUpView();
    View goToBlockedUsersView();
    View goToContentCreationAreaView();
    View goToFriendRequestView();
    View goToMyProfileView();
    View goToNewsFeedView();
    View goToProfileView();
    View goToStoryView();

}

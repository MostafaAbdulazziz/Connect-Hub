package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.backend.api.JavaSocialNetworkAPI;
import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public class FinalNavigationHandler implements NavigationHandler {    @Override
public LoginView goToLoginView() {
    return new LoginView();
}

    @Override
    public SignUpView goToSignUpView() {
        return new SignUpView();

    }

    @Override
    public ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO) {
        return new ContentCreationAreaView(JavaSocialNetworkAPI.getInstance(), userDTO);

    }

    @Override
    public ManageFriendsView goToManageFriendsView(UserDTO userDTO) {
        return new ManageFriendsView(JavaSocialNetworkAPI.getInstance(), userDTO);

    }

    @Override
    public MyProfileView goToMyProfileView(UserDTO userDTO) {
        return new MyProfileView(JavaSocialNetworkAPI.getInstance(), userDTO);

    }

    @Override
    public NewsFeedView goToNewsFeedView(UserDTO userDTO) {
        return new NewsFeedView(JavaSocialNetworkAPI.getInstance(), userDTO);

    }

    @Override
    public ProfileView goToProfileView(UserDTO userDTO) {
        return new ProfileView(JavaSocialNetworkAPI.getInstance(), userDTO);

    }

    @Override
    public StoryView goToStoryView(ContentDTO contentDTO) {
        return new StoryView(contentDTO);
    }
}

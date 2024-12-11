package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.backend.api.JavaSocialNetworkAPI;
import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import javax.swing.*;

public class FinalNavigationHandler implements NavigationHandler {    @Override
    public LoginView goToLoginView() {
        return new LoginView(JavaSocialNetworkAPI.getInstance());
    }

    @Override
    public SignUpView goToSignUpView() {
        return new SignUpView(JavaSocialNetworkAPI.getInstance());

    }

    @Override
    public ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO, boolean isPost) {
        return new ContentCreationAreaView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(userDTO.getUserId()), isPost);

    }

    @Override
    public ManageFriendsView goToManageFriendsView(UserDTO userDTO, JFrame parentFrame) {
        return new ManageFriendsView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(userDTO.getUserId()), parentFrame);

    }

    @Override
    public MyProfileView goToMyProfileView(UserDTO userDTO) {
        return new MyProfileView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(userDTO.getUserId()));

    }

    @Override
    public NewsFeedView goToNewsFeedView(UserDTO userDTO) {
        return new NewsFeedView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(userDTO.getUserId()));

    }

    @Override
    public ProfileView goToProfileView(UserDTO anotherUserDTO, UserDTO userDTO) {
        return new ProfileView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(anotherUserDTO.getUserId()), JavaUserAccountService.getInstance().getUserById(userDTO.getUserId()));

    }

    @Override
    public StoryView goToStoryView(ContentDTO contentDTO) {
        return new StoryView(contentDTO, JavaSocialNetworkAPI.getInstance());
    }

    @Override
    public EditMyProfileView goToEditMyProfileView(UserDTO userDTO) {
        return new EditMyProfileView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(userDTO.getUserId()));
    }
}

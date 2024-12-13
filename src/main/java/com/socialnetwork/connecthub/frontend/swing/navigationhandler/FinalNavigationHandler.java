package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import com.socialnetwork.connecthub.backend.api.JavaSocialNetworkAPI;
import com.socialnetwork.connecthub.backend.service.java.JavaGroupService;
import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.frontend.swing.view.*;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
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
    public ContentCreationAreaView goToContentCreationAreaView(UserDTO userDTO, GroupDTO groupDTO, boolean isPost, boolean isGroup) {
        return new ContentCreationAreaView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(userDTO.getUserId()), isPost,isGroup,groupDTO);
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

    @Override
    public GroupCreationView goToGroupCreationView(UserDTO user) {
        return new GroupCreationView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(user.getUserId()));
    }

    @Override
    public MyGroupsView goToMyGroupsView(UserDTO userDTO) {
        return new MyGroupsView(JavaSocialNetworkAPI.getInstance(), JavaUserAccountService.getInstance().getUserById(userDTO.getUserId()));
    }
    @Override
    public GroupView goToGroupView(GroupDTO groupDTO, UserDTO userDTO) {
        return new GroupView(
                JavaSocialNetworkAPI.getInstance(),
                JavaGroupService.getInstance().getGroupById(groupDTO.getGroupId()),
                JavaUserAccountService.getInstance().getUserById(userDTO.getUserId())
        );
    }

    @Override
    public SearchResultsView goToSearchResultsView(UserDTO user, String query) {
        return new SearchResultsView(
                JavaSocialNetworkAPI.getInstance(),
                user,
                JavaUserAccountService.getInstance().getUsersByUsername(user.getUserId(), query),
                JavaGroupService.getInstance().searchGroupsByName(query)
        );
    }

}

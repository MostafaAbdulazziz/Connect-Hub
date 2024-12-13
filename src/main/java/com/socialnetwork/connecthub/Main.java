package com.socialnetwork.connecthub;

import com.socialnetwork.connecthub.backend.api.JavaSocialNetworkAPI;
import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;
import com.socialnetwork.connecthub.backend.service.java.JavaFriendService;
import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.frontend.swing.view.MyGroupsView;
import com.socialnetwork.connecthub.frontend.swing.view.SearchResultsView;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        NavigationHandlerFactory.getNavigationHandler("final").goToLoginView();
        List<User> users = JsonUserRepository.getInstance().findAll();
        for (int i = 0; i < 2; i++) {
            NavigationHandlerFactory.getNavigationHandler("final").goToNewsFeedView(new UserDTO(users.get(i)));
        }

//        new SearchResultsView(
//                JavaSocialNetworkAPI.getInstance(),
//                new UserDTO(users.get(0)),
//                JavaUserAccountService.getInstance().getUsersByUsername(users.get(0).getUserId(), ""),
//                new ArrayList<>()
//        );

//        NavigationHandlerFactory.getNavigationHandler("final").goToMyGroupsView(new UserDTO(users.get(0)));
    }
}


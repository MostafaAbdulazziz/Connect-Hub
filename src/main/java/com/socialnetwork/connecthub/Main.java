package com.socialnetwork.connecthub;

import com.socialnetwork.connecthub.backend.api.JavaSocialNetworkAPI;
import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.LoginDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        NavigationHandlerFactory.getNavigationHandler("final").goToLoginView();
    }
}


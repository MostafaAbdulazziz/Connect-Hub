package com.socialnetwork.connecthub;

import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public class Main {
    public static void main(String[] args) {
        NavigationHandlerFactory.getNavigationHandler("final").goToLoginView();
    }
}


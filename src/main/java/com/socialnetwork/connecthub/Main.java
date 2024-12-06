package com.socialnetwork.connecthub;

import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;

public class Main {
    public static void main(String[] args) {
        NavigationHandlerFactory.getNavigationHandler("final").goToLoginView();
    }
}


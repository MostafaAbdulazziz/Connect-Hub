package com.socialnetwork.connecthub;

import com.socialnetwork.connecthub.frontend.swing.view.NewsFeedView;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

public class Main {
    public static void main(String[] args) {
        new NewsFeedView(new UserDTO());

    }
}

package com.socialnetwork.connecthub.backend.api;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.interfaces.services.*;
import com.socialnetwork.connecthub.backend.service.java.*;

public class JavaSocialNetworkAPI implements SocialNetworkAPI {
    @Override
    public ContentService getContentService() {
        return JavaContentService.getInstance();
    }

    @Override
    public FriendService getFriendService() {
        return JavaFriendService.getInstance();
    }

    @Override
    public NewsFeedService getNewsFeedService() {
        return JavaNewsFeedService.getInstance();
    }

    @Override
    public ProfileService getProfileService() {
        return JavaProfileService.getInstance();
    }

    @Override
    public UserAccountService getUserAccountService() {
        return JavaUserAccountService.getInstance();
    }
}

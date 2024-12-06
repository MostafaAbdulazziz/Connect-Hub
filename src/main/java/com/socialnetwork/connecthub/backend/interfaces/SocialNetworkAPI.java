package com.socialnetwork.connecthub.backend.interfaces;

import com.socialnetwork.connecthub.backend.interfaces.services.*;

public interface SocialNetworkAPI {
    ContentService getContentService();
    FriendService getFriendService();
    NewsFeedService getNewsFeedService();
    ProfileService getProfileService();
    UserAccountService getUserAccountService();
}

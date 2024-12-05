package com.socialnetwork.connecthub.backend.interfaces.services;

import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import java.util.List;

public interface NewsFeedService {
    List<ContentDTO> getNewsFeed(String userId);
    List<UserDTO> getOnlineFriends(String userId);
    List<UserDTO> getFriendSuggestions(String userId);
}
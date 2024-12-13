package com.socialnetwork.connecthub.backend.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.socialnetwork.connecthub.backend.persistence.json.JsonPostRepository;
import com.socialnetwork.connecthub.backend.service.java.JavaGroupService;
import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import lombok.Data;

import java.util.Date;

@Data
@JsonTypeName("group")
public class GroupNotification extends Notification {
    private String groupId;
    private String contentId;

    public GroupNotification() {}

    public GroupNotification(String groupId) {
        this.groupId = groupId;
        this.message = "You were approved to join groupId " + JavaGroupService.getInstance().getGroupById(groupId).getName();
        this.read = false;
        this.timestamp = new Date();
    }

    public GroupNotification(String groupId, String contentId) {
        this.groupId = groupId;
        this.contentId = contentId;
        this.message = "New Post in "
                + JavaGroupService.getInstance().getGroupById(groupId).getName()
                + " by " + JavaUserAccountService.getInstance().getUserById(
                        JsonPostRepository.getInstance().findById(contentId).orElseThrow().getAuthorId()).getUsername();
        this.read = false;
        this.timestamp = new Date();
    }
}

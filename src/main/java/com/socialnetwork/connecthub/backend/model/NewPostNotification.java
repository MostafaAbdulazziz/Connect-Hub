package com.socialnetwork.connecthub.backend.model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.socialnetwork.connecthub.backend.persistence.json.JsonPostRepository;
import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import lombok.Data;

import java.util.Date;

@Data
@JsonTypeName("newPost")
public class NewPostNotification extends Notification {
    String contentId;

    public NewPostNotification() {}

    public NewPostNotification(String contentIdO) {
        this.contentId = contentIdO;
        this.message = "New Post from your friend " + JavaUserAccountService.getInstance().getUserById(
                JsonPostRepository.getInstance().findById(contentId).orElseThrow().getAuthorId()).getUsername();
        this.read = false;
        this.timestamp = new Date();
    }
}

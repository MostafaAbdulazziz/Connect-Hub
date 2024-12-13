package com.socialnetwork.connecthub.backend.model;

import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import lombok.Data;

import java.util.Date;

@Data
public class NewPostNotification extends Notification {
    ContentDTO contentDTO;

    public NewPostNotification(ContentDTO contentDTO) {
        this.contentDTO = contentDTO;
        this.message = "New Post from your friend " + JavaUserAccountService.getInstance().getUserById(contentDTO.getAuthorId()).getUsername();
        this.read = false;
        this.timestamp = new Date();
    }
}

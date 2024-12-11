package com.socialnetwork.connecthub.shared.dto;

import com.socialnetwork.connecthub.backend.model.Content;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ContentDTO {
    private String authorId;
    private String content;
    private String imagePath;
    private Date timestamp;

    public ContentDTO(Content content) {
        this.authorId = content.getAuthorId();
        this.content = content.getContent();
        this.imagePath = content.getImagePath();
        this.timestamp = content.getTimestamp();
    }

    public ContentDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public ContentDTO setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public ContentDTO setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}

package com.socialnetwork.connecthub.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GroupDTO {
    private String groupId;
    private String name;
    private String description;
    private String iconPhotoPath;
    private String primaryAdmin;

    public GroupDTO(String name, String description, String iconPhotoPath, String primaryAdmin) {
        this.name = name;
        this.description = description;
        this.iconPhotoPath = iconPhotoPath;
        this.primaryAdmin = primaryAdmin;
    }
}

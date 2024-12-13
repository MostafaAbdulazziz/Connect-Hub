package com.socialnetwork.connecthub.shared.dto;

import com.socialnetwork.connecthub.backend.model.Group;
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

    public GroupDTO(Group group) {
        this.name = group.getName();
        this.description = group.getDescription();
        this.iconPhotoPath = group.getIconPhotoPath();
        this.primaryAdmin = group.getPrimaryAdmin();
    }

    public GroupDTO setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public GroupDTO setName(String name) {
        this.name = name;
        return this;
    }

    public GroupDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public GroupDTO setIconPhotoPath(String iconPhotoPath) {
        this.iconPhotoPath = iconPhotoPath;
        return this;
    }

    public GroupDTO setPrimaryAdmin(String primaryAdmin) {
        this.primaryAdmin = primaryAdmin;
        return this;
    }
}

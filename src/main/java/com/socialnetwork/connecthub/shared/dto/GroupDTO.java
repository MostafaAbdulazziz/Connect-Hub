package com.socialnetwork.connecthub.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupDTO {
    private String name;
    private String description;
    private String iconPhotoPath;
    private String primaryAdmin;
}

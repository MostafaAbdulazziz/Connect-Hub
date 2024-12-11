package com.socialnetwork.connecthub.backend.model;

import lombok.Data;

import java.util.List;

@Data
public class Group {
    private String groupId;
    private String name;
    private String description;
    private String iconPhotoPath;
    private String primaryAdmin;
    private List<String> admins;
    private List<String> members;
    private List<String> posts;
    private List<String> requests;
}

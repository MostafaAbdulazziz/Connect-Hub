package com.socialnetwork.connecthub.shared.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ContentDTO {
    private String authorId;
    private String content;
    private String imagePath;
    private Date timestamp;
}

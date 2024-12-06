package com.socialnetwork.connecthub.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class ContentDTO {
    private String authorId;
    private String content;
    private String imagePath;
    private Date timestamp;
}

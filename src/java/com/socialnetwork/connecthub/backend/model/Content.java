package java.com.socialnetwork.connecthub.backend.model;

import lombok.Data;
import java.util.Date;

@Data
public class Content {
    private String contentId;
    private String authorId;
    private String content;
    private String imagePath;
    private Date timestamp;
}

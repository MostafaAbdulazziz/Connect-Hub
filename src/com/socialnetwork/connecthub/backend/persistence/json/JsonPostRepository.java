package com.socialnetwork.connecthub.backend.persistence.json;

import com.socialnetwork.connecthub.backend.model.Post;
import com.socialnetwork.connecthub.backend.persistence.repository.PostRepository;
import com.socialnetwork.connecthub.util.JsonFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonPostRepository implements PostRepository {
    private static final String FILE_PATH = "src/com/socialnetwork/connecthub/resources/data/posts.json";
    private static JsonPostRepository instance;
    private final JsonFileUtil<Post> jsonFileUtil = new JsonFileUtil<>(Post[].class);
    private List<Post> posts;

    private JsonPostRepository() {
        posts = new ArrayList<>(jsonFileUtil.loadFromFile(FILE_PATH));
    }

    public static synchronized JsonPostRepository getInstance() {
        if (instance == null) {
            instance = new JsonPostRepository();
        }
        return instance;
    }

    @Override
    public Optional<Post> findById(String postId) {
        for (Post post : posts) {
            if (post.getContentId().equals(postId)) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    @Override
    public void save(Post post) {
        boolean postExists = false;
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getContentId().equals(post.getContentId())) {
                posts.set(i, post);
                postExists = true;
                break;
            }
        }
        if (!postExists) {
            posts.add(post);
        }
        jsonFileUtil.saveToFile(FILE_PATH, posts);
    }

    @Override
    public void delete(String postId) {
        for (int i = 0; i < posts.size(); i++) {
            if (posts.get(i).getContentId().equals(postId)) {
                posts.remove(i);
                break;
            }
        }
        jsonFileUtil.saveToFile(FILE_PATH, posts);
    }

    @Override
    public List<String> getAllPostIds() {
        List<String> postIds = new ArrayList<>();
        for (Post post : posts) {
            postIds.add(post.getContentId());
        }
        return postIds;
    }
}

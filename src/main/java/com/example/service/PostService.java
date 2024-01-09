package com.example.service;
import com.example.repository.PostRepository;
import com.example.model.Post;

import java.util.List;

public class PostService {
    private PostRepository postRepository;

    public PostService() {
        this.postRepository = new PostRepository();
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

    public Post getById(Long id) {
        return postRepository.getById(id);
    }

    public Post create(Post post) {
        int isInsert = postRepository.create(post);

        if (isInsert > 0) {
            Post postLatest = postRepository.getLatest();
            return postLatest;
        } else {
            return null;
        }
    }

    public Post update(Post post) {
        int isUpdate = postRepository.update(post);
        if (isUpdate > 0) {
            Post postUpdated = postRepository.getById(post.getId());
            return postUpdated;
        } else {
            return null;
        }
    }

    public boolean deleteById(Long id) {
        return postRepository.deleteById(id) > 0 ? true : false;
    }
}

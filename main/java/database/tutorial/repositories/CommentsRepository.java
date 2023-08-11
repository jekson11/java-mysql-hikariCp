package database.tutorial.repositories;

import database.tutorial.Entity.Comments;

import java.util.List;

public interface CommentsRepository {
    void insert(Comments comments);
    Comments findById(Integer id);
    List<Comments> findAll();
    List<Comments> findAllByEmail(String email);
}

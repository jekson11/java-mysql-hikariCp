package database.tutorial;

import database.tutorial.Entity.Comments;
import database.tutorial.repositories.CommentsRepository;
import database.tutorial.repositories.CommentsRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RepositoryTest {

    CommentsRepository commentsRepository;

    @BeforeEach
    void setUp() {
        commentsRepository = new CommentsRepositoryImpl();
    }

    @Test
    void testInsert(){
        Comments comment = new Comments("tams@test", "test");
        commentsRepository.insert(comment);

        Assertions.assertNotNull(comment.getId());
        String expect = "tams@test";
        Assertions.assertEquals(expect,comment.getEmail());
        System.out.println(comment.getId());
    }

    @Test
    void testFindId(){
        Comments comments = commentsRepository.findById(39046);
        Assertions.assertNotNull(comments);

        System.out.println(comments.getId());
        System.out.println(comments.getEmail());
        System.out.println(comments.getComment());

        Comments comments2 = commentsRepository.findById(10000);
        Assertions.assertNull(comments2);
    }

    @Test
    void testFinAll(){
        List<Comments> comments = commentsRepository.findAll();
        System.out.println(comments.size());
        Assertions.assertNotNull(comments);
        comments.forEach(data -> System.out.println(data.getId()));
     }

     @Test
    void testFinAllByEmail(){
         List<Comments> comments = commentsRepository.findAllByEmail("jeks@test");
         System.out.println(comments.size());
         Assertions.assertNotNull(comments);
         comments.forEach(data -> System.out.println(data.getId()));

         List<Comments> comments2 = commentsRepository.findAllByEmail("salah@test");
         System.out.println(comments2.size());
     }
}

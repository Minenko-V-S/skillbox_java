package main.repositories;

import main.model.PostComments;
import main.model.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentsRepository extends JpaRepository<PostComments, Integer> {
    List<PostComments> findByPost(Posts post);
}
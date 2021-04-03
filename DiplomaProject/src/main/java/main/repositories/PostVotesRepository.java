package main.repositories;

import main.model.PostVotes;
import main.model.Posts;
import main.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostVotesRepository extends JpaRepository<PostVotes, Integer> {
    List<PostVotes> findByPostAndValue(Posts post, byte value);
    PostVotes findByUserAndPost(Users user, Posts post);
}

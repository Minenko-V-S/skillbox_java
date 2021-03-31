package main.repositories;


import main.enums.ModerationStatus;
import main.model.Posts;
import main.model.Tags;
import main.model.Users;
import main.model.dto.ModeratedPostDTO;
import main.model.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.Instant;
import java.util.List;


@Repository
public interface PostsRepository extends JpaRepository<Posts, Integer> {
    /**
     * Должны выводиться только активные (поле “is_active” в таблице “posts” равно 1,
     * утверждённые модератором (поле “moderation_status” равно “ACCEPTED”) посты с
     * датой публикации не позднее текущего момента (движок должен позволять
     * откладывать публикацию)
     */

    String QUERY = "SELECT" +
            "    new main.model.dto.PostDTO(" +
            "        p, " +
            "        SUM(CASE WHEN v.value = 1 THEN 1 ELSE 0 END) as like_count, " +
            "        SUM(CASE WHEN v.value = -1 THEN 1 ELSE 0 END)" +
            "    ) " +
            "FROM Posts p " +
            "LEFT JOIN p.votes as v ";

    String WHERE = "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= :date ";
    String GROUP_BY = "GROUP BY p.id";

    String FULL_QUERY = QUERY + WHERE + GROUP_BY;

    @Query(FULL_QUERY)
    Page<PostDTO> findAllPosts(@Param("date") Instant date, Pageable pageable);

    List<Posts> findByModerationStatus(ModerationStatus moderationStatus);

    @Query("SELECT SUM(p.viewCount) FROM Posts p WHERE (:user IS NULL OR p.author = :user)")
    int getViewsByUser(@Param("user") Users user);

    @Query("SELECT DATE_FORMAT(MIN(p.time),'%Y-%m-%d %H:%m') " +
            "FROM Posts p WHERE (:user IS NULL OR p.author = :user)")
    String getFirstPostDateByUser(@Param("user") Users user);

    @Query(QUERY + WHERE + " AND DATE_FORMAT(p.time, '%Y-%m-%d') = str(:date_requested) " + GROUP_BY)
    Page<PostDTO> findAllPostsByDate(
            @Param("date") Instant date,
            @Param("date_requested") String dateRequested,
            Pageable pageable);

    @Query(QUERY + " JOIN p.tags t " + WHERE + " AND t = :tag " + GROUP_BY)
    Page<PostDTO> findAllPostsByTag(
            @Param("date") Instant date,
            @Param("tag") Tags tag,
            Pageable pageable);

    @Query(QUERY + WHERE +
            " AND (" +
            "   p.title LIKE %:query% OR p.text LIKE %:query%" +
            " ) " + GROUP_BY)
    Page<PostDTO> findAllPostsByQuery(
            @Param("date") Instant date,
            @Param("query") String query,
            Pageable pageable);

    /**
     * Counts total number of posts to be moderated by any moderator
     * `isActive = 1` AND `moderationStatus = NEW` AND `moderatedBy = NULL`
     *
     * @return int Returns total amount of posts to be moderated
     */
    @Query("SELECT COUNT (*) FROM Posts p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'NEW' AND p.moderatedBy IS NULL")
    int countPostAwaitingModeration();

    @Query("SELECT COUNT(*) FROM Posts p " + WHERE)
    int countActivePosts(@Param("date") Instant date);

    @Query("SELECT COUNT(*) FROM Posts p JOIN p.tags t " + WHERE + " AND t = :tag GROUP BY t.id")
    int countActivePostsByTag(@Param("date") Instant date, @Param("tag") Tags tag);

    @Query("SELECT COUNT(*) FROM Posts p WHERE (:user IS NULL OR p.author = :user)")
    int countByAuthor(@Param("user") Users user);

    @Query("SELECT " +
            "    new main.model.dto.ModeratedPostDTO(" +
            "       p.id, p.time, p.author, p.title, p.text" +
            "    ) " +
            "FROM Posts p " +
            "WHERE p.isActive = true AND p.moderationStatus = :status AND (:user IS NULL OR p.moderatedBy = :user)")
    Page<ModeratedPostDTO> findModeratedPosts(@Param("user") Users user,
                                              @Param("status") ModerationStatus status,
                                              Pageable pageable);

    @Query(QUERY + "WHERE p.author = :user AND p.isActive = :is_active " +
            "AND (:status IS NULL OR p.moderationStatus = :status) " + GROUP_BY)
    Page<PostDTO> findMyPosts(@Param("user") Users user,
                              @Param("is_active") boolean isActive,
                              @Param("status") ModerationStatus status,
                              Pageable pageable);
}
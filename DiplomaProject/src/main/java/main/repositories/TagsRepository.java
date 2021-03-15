package main.repositories;


import main.model.Posts;
import main.model.Tags;
import main.model.dto.TagDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TagsRepository extends CrudRepository<Tags, Integer> {
    String SELECT_DTO = "SELECT DISTINCT new main.model.dto.TagDTO(t, COUNT(*) as cnt) ";
    String SELECT = "SELECT DISTINCT t ";
    String QUERY = "FROM Tags t " +
            "JOIN t.posts p " +
            "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= NOW() ";
    String GROUP_BY = "GROUP BY t.id";

    @Query(SELECT_DTO + QUERY + GROUP_BY + " ORDER BY cnt DESC, t.name ASC ")
    List<TagDTO> findAllTags();

    @Query(SELECT_DTO + QUERY + " AND t.name LIKE %:name% " + GROUP_BY)
    List<TagDTO> findAllTagsByNameContaining(String name);

    Tags findByNameIgnoreCase(String name);

    @Query("SELECT t.name FROM Tags t JOIN t.posts p WHERE p = :post")
    List<String> findTagNamesByPost(@Param("post") Posts post);
}
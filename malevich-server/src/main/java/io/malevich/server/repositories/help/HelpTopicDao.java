package io.malevich.server.repositories.help;

import io.malevich.server.domain.HelpTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HelpTopicDao extends JpaRepository<HelpTopicEntity, Long> {

    @Query("select ht from HelpTopicEntity ht where ht.helpCategory.id=:id")
    List<HelpTopicEntity> findByCategoryId(@Param("id") Long id);

    @Query(value = "SELECT * FROM help_topic ht WHERE " +
            "lower(JSON_UNQUOTE(JSON_EXTRACT(topic_name_ml,CONCAT('$.',:lang)))) LIKE CONCAT('%',lower(:searchValue),'%') OR " +
            "lower(JSON_UNQUOTE(JSON_EXTRACT(body_ml,CONCAT('$.',:lang)))) LIKE CONCAT('%',lower(:searchValue),'%')",
            nativeQuery = true)
    List<HelpTopicEntity> filter(@Param("lang") String lang, @Param("searchValue") String searchValue);
}

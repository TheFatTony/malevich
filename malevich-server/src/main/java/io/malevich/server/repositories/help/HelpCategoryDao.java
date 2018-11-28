package io.malevich.server.repositories.help;

import io.malevich.server.domain.HelpCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpCategoryDao extends JpaRepository<HelpCategoryEntity, Long> {

}

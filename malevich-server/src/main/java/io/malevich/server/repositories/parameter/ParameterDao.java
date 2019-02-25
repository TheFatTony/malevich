package io.malevich.server.repositories.parameter;

import io.malevich.server.domain.ParameterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterDao extends JpaRepository<ParameterEntity, String> {

}

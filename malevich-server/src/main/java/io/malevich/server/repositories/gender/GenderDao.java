package io.malevich.server.repositories.gender;


import io.malevich.server.domain.GenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderDao extends JpaRepository<GenderEntity, String> {

}


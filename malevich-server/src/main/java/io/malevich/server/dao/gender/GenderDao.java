package io.malevich.server.dao.gender;


import io.malevich.server.entity.GenderEntity;
import io.malevich.server.entity.TraderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenderDao extends JpaRepository<GenderEntity, String> {

}

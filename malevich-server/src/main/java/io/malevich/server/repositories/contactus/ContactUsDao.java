package io.malevich.server.repositories.contactus;

import io.malevich.server.domain.ContactUsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsDao extends JpaRepository<ContactUsEntity, Long> {
}

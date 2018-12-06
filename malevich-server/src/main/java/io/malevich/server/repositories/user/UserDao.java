package io.malevich.server.repositories.user;


import io.malevich.server.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Long> {

    UserEntity findByName(String name);

    @Modifying
    @Query("update user ue set ue.activityFlag = :flag where ue.name = :name")
    void lock(@Param("name") String name, @Param("flag") boolean flag);

    @Modifying
    @Query("update user ue set ue.password = :pass where ue.name = :name")
    void changePassword(@Param("name") String name, @Param("pass") String pass);
}

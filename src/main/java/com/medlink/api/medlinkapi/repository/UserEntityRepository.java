package com.medlink.api.medlinkapi.repository;



import com.medlink.api.medlinkapi.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLogin(String login);
}

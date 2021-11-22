package com.medlink.api.medlinkapi.repository;


import com.medlink.api.medlinkapi.entity.UserEntity;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EnableJdbcRepositories
public interface UserEntityRepository{
    UserEntity findByLogin(String login);

    UserEntity save(UserEntity userEntity);
}

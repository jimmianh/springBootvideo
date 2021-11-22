package com.medlink.api.medlinkapi.entity;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name = "role_table")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strantegy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

}

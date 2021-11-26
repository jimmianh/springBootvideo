package com.medlink.api.medlinkapi.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "drg_drug")
@Data
public class DrugEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String drug_Id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private String unit;

    @Column
    private double price;

    @Column
    private int quantity;


}

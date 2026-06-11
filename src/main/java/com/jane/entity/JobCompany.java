package com.jane.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity
@Table
public class JobCompany {

    @Id
    private Integer id;

    private String name;
    private String logo;

    public JobCompany(int companyId, String name, String s) {
        this.id = companyId;
        this.name = name;
        this.logo = s;
    }

    //TODO [Reverse Engineering] generate columns from DB
}
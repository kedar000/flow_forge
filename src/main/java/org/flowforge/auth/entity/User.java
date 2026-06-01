package org.flowforge.auth.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import org.flowforge.common.entity.BaseEntity;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}
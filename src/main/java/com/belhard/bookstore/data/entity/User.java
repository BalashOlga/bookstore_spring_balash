package com.belhard.bookstore.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name ="users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "roles_id")
    private Role role;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
}
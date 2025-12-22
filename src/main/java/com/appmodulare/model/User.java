package com.appmodulare.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "full_name", length = 150)
    private String fullName;

    @Column(length = 50, unique = true)
    private String username;

    @Column(name = "password_hash", length = 255)
    private String passwordHash;

    @Column(length = 20)
    private String role;

   @Column(name = "photo_url", length = 255)
    private String photoUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }*/
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @EqualsAndHashCode.Include
   private Integer idUser;

    @Column(name = "full_name", length = 150)
    private String fullName;

    @Column(nullable = false, length = 60, unique = true)
    private String username;

    @Column(nullable = false, length = 60) //12345678 Bcrypt hash
    private String password;

    @Column(nullable = false)
    private boolean enabled;
    @Column(name = "photo_url", length = 255)
    private String photoUrl;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "idRole")
    )

    private List<Role> roles;


}
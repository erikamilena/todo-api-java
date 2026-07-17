package com.projects.ToDo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

     @Id
     @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false)
     private String name;

     @Column(nullable = false, unique = true)
     private String email;
}

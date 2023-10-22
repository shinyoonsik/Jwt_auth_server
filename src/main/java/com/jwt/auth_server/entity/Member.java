package com.jwt.auth_server.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "MEMBER")
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username; // email
    private String password;
    private String roles; // USER, ADMIN, SUPER_ADMIN

    public List<String> getRoleList(){
        if(!this.roles.isBlank()){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}

package com.jwt.auth_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "MEMBER")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String roles; // USER, ADMIN, SUPER_ADMIN

    public List<String> getRoleList(){
        if(!this.roles.isBlank()){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
}

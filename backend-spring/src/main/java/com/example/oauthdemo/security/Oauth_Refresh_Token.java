package com.example.oauthdemo.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Oauth_Refresh_Token {

    @Id
    private String token_id;

    @Column(columnDefinition="blob")
    private String token;

    @Column(columnDefinition="blob")
    private String authentication;

}

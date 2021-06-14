package com.example.oauthdemo.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Oauth_Access_Token {

    private String token_id;

    @Column(columnDefinition="blob")
    private String token;

    @Id
    private String authentication_id;

    private String user_name;

    private String client_id;

    @Column(columnDefinition="blob")
    private String authentication;

    private String refresh_token;


    protected Oauth_Access_Token() { }



}

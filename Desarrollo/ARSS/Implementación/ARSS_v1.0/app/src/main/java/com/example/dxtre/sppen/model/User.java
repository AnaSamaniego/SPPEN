package com.example.dxtre.sppen.model;

/**
 * Created by DXtre on 12/07/16.
 */

public class User extends City{

    private int idUser;
    private String email;
    private String password;
    private int facebookLogin;

    public User() {
    }

    public User(int idUser, String email, String password, int facebookLogin) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.facebookLogin = facebookLogin;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFacebookLogin() {
        return facebookLogin;
    }

    public void setFacebookLogin(int facebookLogin) {
        this.facebookLogin = facebookLogin;
    }
}

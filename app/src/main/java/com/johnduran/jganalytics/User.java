package com.johnduran.jganalytics;

/**
 * Created by John Duran on 27/10/2017.
 */

public class User {
    private String name,email,contrasena;

    public User(String email,String name ,String contrasena) {
        this.email = email;
        this.name = name;
        this.contrasena= contrasena;

    }
    public User() {//este lo pide firebase
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

}

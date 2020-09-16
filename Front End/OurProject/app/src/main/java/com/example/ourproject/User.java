package com.example.ourproject;


public class User {

    private String username,
            fullname,
            ID,
            email,
            password,
            type;


    public User(String username, String fullname, String ID, String email, String password, String type) {
        this.username = username;
        this.fullname = fullname;
        this.ID = ID;
        this.email = email;
        this.password = password;
        this.type = type;
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.fullname = "";
        this.email = "";
        this.type = "";
        this.ID = "";
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setID(String student_id) {
        this.ID = student_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}

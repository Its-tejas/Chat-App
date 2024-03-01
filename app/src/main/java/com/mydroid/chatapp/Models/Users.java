package com.mydroid.chatapp.Models;

public class Users {
    public int img;
    public String name;
    public String email;

    public String password;

    public String userid;
    public String lastmsg;

    public Users() {
    }

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Users(int img, String name, String email, String password, String userid, String lastmsg) {
        this.img = img;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.lastmsg = lastmsg;
    }

    public Users(int img, String name, String lastmsg) {
        this.img = img;
        this.name = name;
        this.lastmsg = lastmsg;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

package com.mydroid.chatapp.Models;

public class Users {
    public String profilepic, name, email, password, userid, lastmsg;

    public int img;

    public Users( int img, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.img = img;
    }

    public Users() {
    }

    public Users(String profilepic, String name, String email, String password, String userid, String lastmsg) {
        this.profilepic = profilepic;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.lastmsg = lastmsg;
    }

    public Users(String profilepic, String name, String email, String password) {
        this.profilepic = profilepic;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmsg() {
        return lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        this.lastmsg = lastmsg;
    }
}
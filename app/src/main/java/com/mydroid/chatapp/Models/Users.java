package com.mydroid.chatapp.Models;

public class Users {
    public String profilePic, name, email, password, userid, lastmsg, about;



    public Users() {
    }

    public Users(String profilePic, String name, String email, String password, String userid, String lastmsg, String about) {
        this.profilePic = profilePic;
        this.name = name;
        this.email = email;
        this.password = password;
        this.userid = userid;
        this.lastmsg = lastmsg;
        this.about = about;
    }

    public Users(String profilePic, String name, String email, String password) {
        this.profilePic = profilePic;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Users(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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
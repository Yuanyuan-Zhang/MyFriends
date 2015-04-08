package com.example.student.myfriends;

/**
 * Created by Yuanyuan Zhang on 2/25/15.
 */
public class Friends {

    private int id_;
    private String name_;
    private String email_;
    private String phone_;

    public Friends() {

    }

    public Friends(int id, String name, String email, String phone) {
        this.id_ = id;
        this.name_ = name;
        this.email_ = email;
        this.phone_ = phone;
    }

    public void setID(int id) {
        this.id_ = id;
    }

    public int getID() {
        return this.id_;
    }

    public void setName(String name) {
        this.name_ = name;
    }

    public String getName() {
        return this.name_;
    }

    public void setEmail(String email) {
        this.email_ = email;
    }

    public String getEmail() {
        return this.email_;
    }

    public void setPhone(String phone){
        this.phone_ = phone;
    }

    public String getPhone(){

        return this.phone_;
    }
}
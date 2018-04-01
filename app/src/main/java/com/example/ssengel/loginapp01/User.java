package com.example.ssengel.loginapp01;

/**
 * Created by ssengel on 01.04.2018.
 */


//interface abdullah arastiracak
public class User {

    private String id;
    private String userName;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private int age;
    private String recordTime;

    public User(){
        this.id = null;
        this.userName = null;
        this.password = null;
        this.name = null;
        this.lastName = null;
        this.email = null;
        this.age = 0;
        this.recordTime = null;
    }

    public User(String id, String userName, String password, String name, String lastName, String email, int age, String recordTime) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.recordTime = recordTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", recordTime='" + recordTime + '\'' +
                '}';
    }
}

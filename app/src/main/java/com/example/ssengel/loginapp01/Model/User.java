package com.example.ssengel.loginapp01.Model;

/**
 * Created by ssengel on 01.04.2018.
 */


//interface abdullah arastiracak
public class User {

    private String id;
    private String companyId;
    private String storeId;
    private String userName;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String recordTime;

    public User(){
        this.id = null;
        this.companyId = null;
        this.storeId = null;
        this.userName = null;
        this.password = null;
        this.name = null;
        this.lastName = null;
        this.email = null;
        this.recordTime = null;
    }

    public User(String id, String companyId,String storeId, String userName, String password, String name, String lastName, String email, String recordTime) {
        this.id = id;
        this.storeId = storeId;
        this.companyId = companyId;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.email = email;

        this.recordTime = recordTime;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
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
                ", companyId='" + companyId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", recordTime='" + recordTime + '\'' +
                '}';
    }
}

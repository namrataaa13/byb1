package com.example.bookyourbooksigninup;

public class ModelClass {

    private String name, id, category, categoryid, email, pincode, phone, city, option, picture;

    public ModelClass(){

    }

    public ModelClass(String name, String id, String category, String categoryid, String pincode, String email, String phone, String city, String option, String picture) {
        this.name = name;
        this.id = id;
        this.category = category;
        this.categoryid = categoryid;
        this.email = email;
        this.pincode = pincode;
        this.phone = phone;
        this.city = city;
        this.option = option;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

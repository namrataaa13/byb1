package com.example.bookyourbooksigninup;

public class ModelCategoryClass {

    private String srno;
    private String email;
    private String bookcategoryname;

    public ModelCategoryClass(String srno, String email, String bookcategoryname) {
        this.srno = srno;
        this.email = email;
        this.bookcategoryname = bookcategoryname;
    }

    public ModelCategoryClass(){

    }
    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBookcategoryname() {
        return bookcategoryname;
    }

    public void setBookcategoryname(String bookcategoryname) {
        this.bookcategoryname = bookcategoryname;
    }

}

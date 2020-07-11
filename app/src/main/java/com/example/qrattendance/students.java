package com.example.qrattendance;

public class students {
    String branch;
    String name;
    String year;
    String semester;
    String rollno;
    String phone;
    String password;
    public  students()
    {

    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public students(String branch, String name, String year, String semester, String rollno, String phone, String password) {
        this.branch = branch;
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.rollno = rollno;
        this.phone = phone;
        this.password = password;
    }


}

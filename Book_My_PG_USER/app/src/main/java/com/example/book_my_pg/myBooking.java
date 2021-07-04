package com.example.book_my_pg;

public class myBooking {

    String name, add, city, phoneno;

    public myBooking(String name, String add, String city, String phoneno) {
        this.name = name;
        this.phoneno = phoneno;
        this.add = add;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() { return name; }

    public void setName(String uname) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

}
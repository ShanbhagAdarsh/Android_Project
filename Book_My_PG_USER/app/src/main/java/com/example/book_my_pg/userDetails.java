package com.example.book_my_pg;

public class userDetails {

    public userDetails(){

    }

    String uname,age,roomno,addresss,phoneno,uid,city,pgname,pgadd,pgph;
    public  userDetails(String roomno,String uname,String phoneno,String age,String addresss,String uid,String city,String pgname,String pgadd,String pgph){
        this.roomno=roomno;
        this.uname=uname;
        this.phoneno=phoneno;
        this.age=age;
        this.addresss=addresss;
        this.uid = uid;
        this.city=city;
        this.pgname=pgname;
        this.pgadd=pgadd;
        this.pgph=pgph;
    }

    public String getPgadd() {
        return pgadd;
    }

    public void setPgadd(String pgadd) {
        this.pgadd = pgadd;
    }

    public String getPgph() {
        return pgph;
    }

    public void setPgph(String pgph) {
        this.pgph = pgph;
    }

    public String getPgname() {
        return pgname;
    }

    public void setPgname(String pgname) {
        this.pgname = pgname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


}
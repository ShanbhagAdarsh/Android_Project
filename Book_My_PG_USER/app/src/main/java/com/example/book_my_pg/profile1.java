package com.example.book_my_pg;

public class profile1 {

    public String userFName,userEmail,userMobileno,DOB,Age,Gender,userLocalAddress,userAREA,userPINCODE,userCITY,userSTATE,userCurrentAddress,userCAREA,userCPINCODE,userCCITY,userCSTATE,Work;
    public profile1(){
    }

    public profile1(String userFName,String userEmail,String userMobileno,String DOB,String Age,String Gender,String userLocalAddress,
                    String userAREA,String userPINCODE,String userCITY,String userSTATE,String userCurrentAddress,
                    String userCAREA,String userCPINCODE,String userCCITY,String userCSTATE,String Work){
        this.userFName=userFName;
        this.userEmail=userEmail;
        this.userMobileno=userMobileno;
        this.DOB=DOB;
        this.userLocalAddress=userLocalAddress;
        this.userAREA = userAREA;
        this.userPINCODE = userPINCODE;
        this.userSTATE = userSTATE;
        this.userCITY = userCITY;
        this.userCurrentAddress=userCurrentAddress;
        this.userCAREA = userCAREA;
        this.userCPINCODE = userCPINCODE;
        this.userCSTATE = userCSTATE;
        this.userCCITY = userCCITY;
        this.Age=Age;
        this.Gender=Gender;
        this.Work=Work;
    }
    /*public profile1(String Name,String Phone_Number,String Native_address,String Age){
        this.Name=Name;
        this.Phone_Number=Phone_Number;
        this.Native_address=Native_address;
        this.Age=Age;
    }*/

}

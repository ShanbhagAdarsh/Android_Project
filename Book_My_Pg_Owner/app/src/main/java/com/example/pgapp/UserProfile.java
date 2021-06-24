package com.example.pgapp;

public class UserProfile {

    public String userFName;
    public String userLName;
    public  String userEmail,userMobileno,userLocalAddress,userAREA,userPINCODE,userSTATE,userCITY;
    public  String myratting;



    private UserProfile(){

    }

    public UserProfile(String myratting)
    {
        this.myratting = myratting;
    }


    public UserProfile(String userFName,String userLName,String userEmail,String userMobileno,String userLocalAddress,String userAREA,String userPINCODE,String userCITY,String userSTATE){
        this.userFName = userFName;
        this.userLName = userLName;
        this.userEmail = userEmail;
        this.userMobileno = userMobileno;
        this.userLocalAddress = userLocalAddress;
        this.userAREA = userAREA;
        this.userPINCODE = userPINCODE;
        this.userSTATE = userSTATE;
        this.userCITY = userCITY;
    }



    public String getMyratting(){
        return myratting;
    }

    public void setMyratting(String rate){
        this.myratting = rate;
    }

    public String getUserFName(){
        return userFName;
    }

    public void setUserFName(String userName){
        this.userFName = userName;
    }

    public String getUserLName(){
        return userLName;
    }

    public void setUserLName(String userUsn){
        this.userLName = userUsn;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }


    public String getUserMobileno(){
        return userMobileno;
    }

    public void setUserMobileno(String userMobileno){
        this.userMobileno = userMobileno;
    }

    public String getUserLocalAddress(){
        return userLocalAddress;
    }

    public void setUserLocalAddress(String userLocalAddress){
        this.userLocalAddress = userLocalAddress;
    }

    public String getUserAREA(){
        return userAREA;
    }

    public void setUserAREA(String userAREA){
        this.userAREA = userAREA;
    }

    public String getUserPINCODE(){
        return userPINCODE;
    }

    public void setUserPINCODE(String userPINCODE){
        this.userPINCODE = userPINCODE;
    }

    public String getUserCITY(){
        return userCITY;
    }

    public void setUserCITY(String userCITY){
        this.userCITY = userCITY;
    }

    public String getUserSTATE(){
        return userSTATE;
    }

    public void setUserSTATE(String userSTATE){
        this.userSTATE = userSTATE;
    }

}



package com.example.pgapp;

public class RoomProfile {
    public  String pgROOMTYPE,pgROOMNO,pgROOMDETAILS,pgROOMDEPOSIT,pgROOMAMOUNT,pgID,pgName,pgCity,pgAddress,pgMobno;
    public int count;
    private RoomProfile()
    {

    }

    public RoomProfile(String Sroomtype,String Sroomno,String Sroomdetails,String Sroomdepositamt,String Sroomfullamt,String pgID,String pgName,String pgCity,String pgAddress,String pgMobno,int count)
    {
        this.pgROOMTYPE = Sroomtype;
        this.pgROOMNO = Sroomno;
        this.pgROOMDETAILS = Sroomdetails;
        this.pgROOMDEPOSIT = Sroomdepositamt;
        this.pgROOMAMOUNT = Sroomfullamt;
        this.pgID = pgID;
        this.pgName = pgName;
        this.pgCity = pgCity;
        this.pgAddress = pgAddress;
        this.pgMobno = pgMobno;
        this.count = count;
    }

    public String getPgROOMTYPE(){
        return pgROOMTYPE;
    }
    protected void setPgROOMTYPE(String rtype)
    {
        this.pgROOMTYPE = rtype;
    }


    public String getPgROOMNO(){
        return pgROOMNO;
    }
    protected void setPgROOMNO(String rno)
    {
        this.pgROOMNO = rno;
    }

    public String getPgROOMDETAILS(){
        return pgROOMDETAILS;
    }
    protected void setPgROOMDETAILS(String rdetails)
    {
        this.pgROOMDETAILS = rdetails;
    }

    public String getPgROOMDEPOSIT(){
        return pgROOMDEPOSIT;
    }
    protected void setPgROOMDEPOSIT(String rdeposit)
    {
        this.pgROOMDEPOSIT = rdeposit;
    }

    public String getPgROOMAMOUNT(){
        return pgROOMAMOUNT;
    }
    protected void setPgROOMAMOUNT(String ramount)
    {
        this.pgROOMAMOUNT = ramount;
    }


    public String getPgID(){
        return pgID;
    }
    protected void setPgID(String pgID)
    {
        this.pgID = pgID;
    }



    public String getPgName(){
        return pgName;
    }
    protected void setPgName(String pgName)
    {
        this.pgName = pgName;
    }

    public String getPgCity(){
        return pgCity;
    }
    protected void setPgCity(String pgCity)
    {
        this.pgCity = pgCity;
    }


    public String getPgAddress(){
        return pgAddress;
    }
    protected void setPgAddress(String pgAddress)
    {
        this.pgAddress = pgAddress;
    }

    public String getPgMobno(){
        return pgMobno;
    }
    protected void setPgMobno(String pgMobno)
    {
        this.pgMobno = pgMobno;
    }

    public int getCount(){
        return count;
    }
    protected void setCount(int count)
    {
        this.count = count;
    }

}

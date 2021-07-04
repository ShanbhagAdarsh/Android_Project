package com.example.book_my_pg;

import java.util.*;

public class PGprofile {
    public String pgTYPE,pgNAME,pgMAIL,pgOWNERNAME,pgMOBILENO,pgLOCALADDRESS,pgPINCODE,pgCITY,pgSTATE;
    public String IMAGEID,pgId;
    static int i=0;
    public  PGprofile(){

    }

    public PGprofile(String pgtype,String pgname,String pgownername,String pgmail,String pgmobileno,String pgaddress,String pgpincode,String pgcity,String pgstate,String pgId){
        this.pgTYPE = pgtype;
        this.pgNAME = pgname;
        this.pgMAIL = pgmail;
        this.pgOWNERNAME = pgownername;
        this.pgMOBILENO = pgmobileno;
        this.pgLOCALADDRESS = pgaddress;
        this.pgPINCODE = pgpincode;
        this.pgCITY = pgcity;
        this.pgSTATE = pgstate;
        this.pgId = pgId;
    }


    public String getPgTYPE() {
        return pgTYPE;
    }

    public void setPgTYPE(String pgtype) {
        this.pgTYPE = pgtype;
    }

    public String getPgNAME() {
        return pgNAME;
    }

    public void setPgNAME(String pgname) {
        this.pgNAME = pgname;
    }


    public String getPgOWNERNAME() {
        return pgOWNERNAME;
    }

    public void setPgOWNERNAME(String pgownername) {
        this.pgOWNERNAME = pgownername;
    }

    public String getPgMAIL() {
        return pgMAIL;
    }

    public void setPgMAIL(String pgmail) {
        this.pgMAIL = pgmail;
    }

    public String getPgMOBILENO() {
        return pgMOBILENO;
    }

    public void setPgMOBILENO(String pgmobileno) {
        this.pgMOBILENO = pgmobileno;
    }


    public String getPgLOCALADDRESS() {
        return pgLOCALADDRESS;
    }

    public void setPgLOCALADDRESS(String pglocaladdress) {
        this.pgLOCALADDRESS = pglocaladdress;
    }

    public String getPgPINCODE() {
        return pgPINCODE;
    }

    public void setPgPINCODE(String pgpincode) {
        this.pgPINCODE = pgpincode;
    }

    public String getPgCITY() {
        return pgCITY;
    }

    public void setPgCITY(String pgcity) {
        this.pgCITY = pgcity;
    }

    public String getPgSTATE() {
        return pgSTATE;
    }

    public void setPgSTATE(String pgstate) {
        this.pgSTATE = pgstate;
    }

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }

}
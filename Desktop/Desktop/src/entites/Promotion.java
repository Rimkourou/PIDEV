/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class Promotion {
     int id,idUser;
    Date dateD,dateF;
    String critaire;

    public Promotion(int id,String critaire, Date dateD, Date dateF,int idUser) {
        this.id = id;
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
        this.idUser = idUser;
       
    }

    public Promotion(String critaire, Date dateD, Date dateF) {
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
        //this.idUser = idUser;
    }

    public Promotion(int id, String critaire, Date dateD, Date dateF) {
        this.id = id;
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDateD() {
        return dateD;
    }

    public void setDateD(Date dateD) {
        this.dateD = dateD;
    }

    public Date getDateF() {
        return dateF;
    }

    public void setDateF(Date dateF) {
        this.dateF = dateF;
    }

    public String getCritaire() {
        return critaire;
    }

    public void setCritaire(String critaire) {
        this.critaire = critaire;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id=" + id + ", idUser=" + idUser + ", dateD=" + dateD + ", dateF=" + dateF + ", critaire=" + critaire + '}';
    }
    

}

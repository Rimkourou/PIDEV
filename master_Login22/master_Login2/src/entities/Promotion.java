/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import javafx.scene.image.ImageView;

/**
 *
 * @author ASUS
 */
public class Promotion {
     int id,idUser;
    Date dateD,dateF;
    String critaire,castPhoto,reduction;

    public Promotion(int id,String critaire, Date dateD, Date dateF,int idUser,String reduction) {
        this.id = id;
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
        this.idUser = idUser;
        this.reduction=reduction;
      
       
    }

    public Promotion(String critaire, Date dateD, Date dateF,String castPhoto,String reduction) {
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
        this.castPhoto=castPhoto;
        this.reduction=reduction;
       
        //this.idUser = idUser;
    }

    public Promotion(int id, String critaire, Date dateD, Date dateF,String reduction) {
        this.id = id;
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
        this.reduction=reduction;
    }

    public Promotion(int id, String critaire, Date dateD, Date dateF, int idUser, String castPhoto,String reduction) {
        this.id = id;
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
        this.idUser = idUser;
        this.castPhoto=castPhoto;
        this.reduction=reduction;
    }

    public Promotion(int idUser, Date dateD, Date dateF, String critaire, String castPhoto, String reduction) {
        this.idUser = idUser;
        this.dateD = dateD;
        this.dateF = dateF;
        this.critaire = critaire;
        this.castPhoto = castPhoto;
        this.reduction = reduction;
    }
    
    
    public Promotion(int id, String critaire, Date dateD, Date dateF,String castPhoto,String reduction) {
        this.id = id;
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
        this.castPhoto=castPhoto;
        this.reduction=reduction;
    }

    public Promotion(String critaire, Date dateD, Date dateF, int idUser, String castPhoto, String reduction) {
        this.critaire=critaire;
        this.dateD = dateD;
        this.dateF = dateF;
        this.idUser = idUser;
        this.castPhoto=castPhoto;
        this.reduction=reduction;
    }

    public String getCastPhoto() {
        return castPhoto;
    }

    public void setCastPhoto(String castPhoto) {
        this.castPhoto = castPhoto;
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

    public String getReduction() {
        return reduction;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id=" + id + ", idUser=" + idUser + ", dateD=" + dateD + ", dateF=" + dateF + ", critaire=" + critaire + ", castPhoto=" + castPhoto + ", reduction=" + reduction + '}';
    }
    
    

}

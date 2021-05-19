/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.entity;




public class Promotion {
    
    int id,idUser;
    String critaire,castPhoto,reduction;
    String dateD , dateF;

    public Promotion() {
    }

    public Promotion(int id, int idUser, String critaire, String castPhoto, String reduction, String dateD, String dateF) {
        this.id = id;
        this.idUser = idUser;
        this.critaire = critaire;
        this.castPhoto = castPhoto;
        this.reduction = reduction;
        this.dateD = dateD;
        this.dateF = dateF;
    }
  

    public Promotion(int idUser, String critaire, String castPhoto, String reduction, String dateD, String dateF) {
        this.idUser = idUser;
        this.critaire = critaire;
        this.castPhoto = castPhoto;
        this.reduction = reduction;
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

    public String getCritaire() {
        return critaire;
    }

    public void setCritaire(String critaire) {
        this.critaire = critaire;
    }

    public String getCastPhoto() {
        return castPhoto;
    }

    public void setCastPhoto(String castPhoto) {
        this.castPhoto = castPhoto;
    }

    public String getReduction() {
        return reduction;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }

    public String getDateF() {
        return dateF;
    }

    public void setDateF(String dateF) {
        this.dateF = dateF;
    }

  
    
    
    
    
}

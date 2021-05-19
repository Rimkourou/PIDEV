/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.entity;


public class Panier {
    
    private int id ;
    private int iduser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public Panier(int id, int iduser) {
        this.id = id;
        this.iduser = iduser;
    }

    @Override
    public String toString() {
        return "Panier{" + "id=" + id + ", iduser=" + iduser + '}';
    }

    public Panier() {
    }
   
    
    
    
    
}

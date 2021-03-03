/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package table_users;

/**
 *
 * @author ASUS
 */
public class User {
    
    int id;
    String nom,prenom,password,email,role,cin;

    public User(int id,String cin, String nom, String prenom,String role, String email, String password) {
        this.id = id;
        this.cin=cin;
        this.nom = nom;
        this.prenom=prenom;
        this.role = role;
        this.email = email;
        this.password = password;
        
        
    }

    public int getId() {
        return id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

   

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

   

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}

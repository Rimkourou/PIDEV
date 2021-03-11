/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author ASUS
 */
public class User {
     int id;
    String nom,prenom,password,email,role,state;

    public User(int id, String nom, String prenom,String role, String email, String password,String state) {
        this.id = id;
        this.nom = nom;
        this.prenom=prenom;
        this.role = role;
        this.email = email;
        this.password = password;
        this.state=state;
        
    }

    public User(String nom, String prenom, String role, String email, String password,String state) {
        
        this.nom = nom;
        this.prenom=prenom;
        this.role = role;
        this.email = email;
        this.password = password;
        this.state=state;
    }

    public User() {
    }

    public User(String nom, String prenom, String role, String email, String password) {
        this.nom = nom;
        this.prenom=prenom;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", email=" + email + ", role=" + role + ", state=" + state + '}';
    }

}

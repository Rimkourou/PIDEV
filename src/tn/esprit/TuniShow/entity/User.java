/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.TuniShow.entity;

import java.util.Date;


public class User {
    int id;
    String nom,prenom,password,email,role,state;
    String age;
    public User() {
    }

    public User(int id, String nom, String prenom,String age,String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public User(String nom, String prenom, String password, String email, String role, String state, String age) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.role = role;
        this.state = state;
        this.age = age;
    }

    public User(String nom, String prenom, String password, String email, String age) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.age = age;
    }
    
   

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    

    public int getId() {
        return id;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", email=" + email + ", role=" + role + ", state=" + state + '}';
    }

   
   
    
}

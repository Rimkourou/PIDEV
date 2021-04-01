/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitie.ons;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class User {
    int id;
    String nom,prenom,password,email,role,state,s_ques,answer;
    Date age;

    public User(int id, String nom, String prenom,Date age,String role, String email, String password,String state,String s_ques,String answer) {
        this.id = id;
        this.nom = nom;
        this.prenom=prenom;
        this.age=age;
        this.role = role;
        this.email = email;
        this.password = password;
        this.state=state;
        this.s_ques=s_ques;
        this.answer=answer;
       
    }

    public User(String nom, String prenom,Date age,String role, String email, String password,String state,String s_ques,String answer) {
        
        this.nom = nom;
        this.prenom=prenom;
        this.age=age;
        this.role = role;
        this.email = email;
        this.password = password;
        this.state=state;
        this.s_ques=s_ques;
        this.answer=answer;
        
    }

    public User() {
    }

    public User(String nom, String prenom,Date age, String role, String email, String state,String s_ques,String answer) {
        this.nom = nom;
        this.prenom=prenom;
        this.age=age;
        this.role = role;
        this.email = email;
        this.state=state;
        this.s_ques=s_ques;
        this.answer=answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
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

    public String getS_ques() {
        return s_ques;
    }

    public void setS_ques(String s_ques) {
        this.s_ques = s_ques;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", age=" + age + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", email=" + email + ", role=" + role + ", state=" + state + ", s_ques=" + s_ques + ", answer=" + answer + '}';
    }

    
    

    
}

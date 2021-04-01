/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.ons;

import entitie.ons.User;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface iServiceUser{
    public void Add_users(User t) throws SQLException;
    List<User> read() throws SQLException;
    public void Edit(User t) throws SQLException;
    public void Delete(int id) throws SQLException;
    public int countUsersS();
    public int countUsersI();
    public int Totale();
    public User getUser(String email, String password);
    public ObservableList<User> readAdmin() ;
    public ObservableList<User> readMembre();
    //public void Add_Admin(User u) throws SQLException;
   // public void update_table();
    //void search_user();
}

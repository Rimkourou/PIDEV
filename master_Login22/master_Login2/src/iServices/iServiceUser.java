/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iServices;

import entities.User;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author ASUS
 */
public interface iServiceUser <T>{
    public void Add_users(T t) throws SQLException;
    List<T> read() throws SQLException;
    public void Edit(T t) throws SQLException;
    public void Delete(int id) throws SQLException;
    public int countUsersS();
    public int countUsersI();
    public int Totale();
    public User getUser(String email,String password);
    public ObservableList<User> readAdmin() ;
    public ObservableList<User> readMembre();
    //public void Add_Admin(User u) throws SQLException;
   // public void update_table();
    //void search_user();
}

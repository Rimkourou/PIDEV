/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iServices;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface iServiceUser <T>{
    public void Add_users(T t) throws SQLException;
    List<T> read() throws SQLException;
    public void Edit(T t) throws SQLException;
    public void Delete(int id) throws SQLException;
   // public void update_table();
    //void search_user();
}

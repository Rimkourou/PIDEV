/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iService;

import entities.User;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 * @param <T>
 */
public interface iServiceUser<T> {
     public void Add_users(T t) throws SQLException;
     public void login() throws SQLException;
}

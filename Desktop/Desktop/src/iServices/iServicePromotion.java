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
public interface iServicePromotion <T>{
    public void Add_Promotions(T t) throws SQLException;
    List<T> read() throws SQLException;
    public void EditP(T t) throws SQLException;
    public void DeleteP(int id) throws SQLException;
    
}

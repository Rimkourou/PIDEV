/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.ons;

import entitie.ons.Promotion;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface iServicePromotion {
    public void Add_Promotions(Promotion t) throws SQLException;
    List<Promotion> read() throws SQLException;
    public void EditP(Promotion t) throws SQLException;
    public void DeleteP(int id) throws SQLException;
    ObservableList<Promotion> getDataPromotion();
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.ons;


import entitie.ons.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.SingletonConnection;


import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ASUS
 */
public class UserService implements iServiceUser {
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public UserService() {
        conn = SingletonConnection.getInstance().getCnx();
    }


    @Override
    public void Add_users(User u) throws SQLException {
        String sql = "insert into user(nom,prenom,age,role,email,password,state,s_ques,answer) values(?,?,?,?,?,?,?,?,?)";
        pst = conn.prepareStatement(sql);
        pst.setString(1, u.getNom());
        pst.setString(2, u.getPrenom());
        pst.setDate(3, u.getAge());
        pst.setString(4, u.getRole());
        pst.setString(5, u.getEmail());
        pst.setString(6, u.getPassword());
        pst.setString(7, u.getState());
        pst.setString(8, u.getS_ques());
        pst.setString(9, u.getAnswer());
        pst.executeUpdate();
//        Statement st = conn.createStatement();
//        String sql ="insert into user(nom,prenom,age,role,email,password,state,s_ques,answer) values('"+u.getNom()+"','"+u.getPrenom()+"','"+u.getAge()+"','"+u.getRole()+"','"+u.getEmail()+"','"+u.getPassword()+"','"+u.getState()+"','"+u.getS_ques()+"','"+u.getAnswer()+"')";
//        st.executeUpdate(sql);
    }

    @Override
    public ObservableList<User> read() {
        String req = "select * from user";

        ObservableList<User> listM = FXCollections.observableArrayList();
        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(req);
            while (rs.next()) {
                listM.add(new User(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("age"),
                        rs.getString("role"),
                        rs.getString("email"),
                        //rs.getInt("idUser"),
                        rs.getString("password"),
                        rs.getString("state"),
                        rs.getString("s_ques"),
                        rs.getString("answer")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listM;
    }

    @Override
    public ObservableList<User> readAdmin() {

        Connection conn = SingletonConnection.getInstance().getCnx();
        ObservableList<User> list2 = FXCollections.observableArrayList();
        String r = "admin";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement("Select * from user where role='" + r + "' ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list2.add(new User(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("age"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("state"),
                        rs.getString("s_ques"),
                        rs.getString("answer")));
            }
        } catch (Exception e) {
        }
        return list2;
    }

    @Override
    public ObservableList<User> readMembre() {
        Connection conn = SingletonConnection.getInstance().getCnx();
        ObservableList<User> list3 = FXCollections.observableArrayList();
        String r = "membre";
        try {
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement("Select * from user where role='" + r + "' ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list3.add(new User(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("age"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("state"),
                        rs.getString("s_ques"),
                        rs.getString("answer")));
            }
        } catch (Exception e) {
        }
        return list3;
    }

    @Override
    public void Edit(User u) throws SQLException {
        PreparedStatement pt = conn.prepareStatement("update user set nom= ?, prenom= ? ,age=?, role= ? , email=? , password=? ,state=?,s_ques=?,answer=? where id =?");


        pt.setString(1, u.getNom());
        pt.setString(2, u.getPrenom());
        pt.setDate(3, u.getAge());
        pt.setString(4, u.getRole());
        pt.setString(5, u.getEmail());
        pt.setString(6, u.getPassword());
        pt.setString(7, u.getState());
        pt.setString(8, u.getS_ques());
        pt.setString(9, u.getAnswer());

        pt.setInt(10, u.getId());
        pt.executeUpdate();
    }


    @Override
    public void Delete(int id) throws SQLException {
        String sql = "delete from user where id=? ";
        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("User deleted successfully");

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public int countUsersS() {
        int a = 0;

        String sql = "select count(*) from user where YEAR(CURDATE())-YEAR(age) >18";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return a;
    }

    @Override
    public int countUsersI() {
        int a = 0;
        String sql = "select count(*) from user where YEAR(CURDATE())-YEAR(age) <18";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return a;
    }


    @Override
    public int Totale() {
        int a = 0;
        String sql = "select count(*) from user";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery(sql);
            while (rs.next()) {
                a = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return a;
    }

    @Override
    public User getUser(String email, String password) {
        User user = new User();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from user where email=?" + " and password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                //user.setAge(rs.getDate("age"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setState(rs.getString("state"));
                user.setS_ques(rs.getString("s_ques"));
                user.setAnswer(rs.getString("answer"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }


}
    


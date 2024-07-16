/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cp2labproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class User extends UserLogin {

   

    
    public boolean kullaniciGiris(String kullaniciAdi, String sifre) {
    String url = "jdbc:mysql://127.0.0.1:3306/fzt";
    String username = "root";
    String pass = "fsmvu";

    try (Connection conn = DriverManager.getConnection(url, username, pass)) {
        String query = "SELECT * FROM company WHERE id = ? AND username = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, kullaniciAdi);
        statement.setString(2, sifre);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int ID = resultSet.getInt("ID");
            String name = resultSet.getString("Name");
            String Username = resultSet.getString("Username");
            int income = resultSet.getInt("income");
            int tax = resultSet.getInt("tax");
          

            CompanyPage compsayf = new CompanyPage();
            Company compobj = new Company();
            compobj.setName(name);
            compobj.setUsername(    Username);
            compobj.setIncome(income);
            compobj.setTax(tax);
            compsayf.getIDlabel().setText(String.valueOf(ID));
            compsayf.getIsimLabel().setText(name);
            compsayf.getusernameLabel().setText(Username);
            compsayf.getincomeLabel().setText(String.valueOf(income));
            compsayf.gettaxLabel().setText(String.valueOf(tax));
            

            compsayf.setVisible(true);
            

            // Login sayfasını kapat
            dispose();

            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}
    public boolean managerGiris(String kullaniciAdi, String sifre) {
    String url = "jdbc:mysql://127.0.0.1:3306/fzt";
    String username = "root";
    String pass = "fsmvu";

    try (Connection conn = DriverManager.getConnection(url, username, pass)) {
        String query = "SELECT * FROM manager WHERE id = ? AND surname= ?";   
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, kullaniciAdi);
        statement.setString(2, sifre);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int ID = resultSet.getInt("ID");
            String name = resultSet.getString("Name");
            String surname = resultSet.getString("Surname");

            ManagerPage managerSayf = new ManagerPage();
            Manager managerObj = new Manager();
            managerObj.setName(name);
            managerObj.setSurname(surname);
            managerSayf.getIDlabel().setText(String.valueOf(ID));
            managerSayf.getIsimLabel().setText(name);
            managerSayf.getSoyIsimLabel().setText(surname);

            managerSayf.setVisible(true);
            JOptionPane.showMessageDialog(this, "Kullanıcı girişi başarılı.");

            // Login sayfasını kapat
            dispose();

            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

    
    
    public boolean adminGiris(String kullaniciAdi, String sifre) {
        // Kullanıcı adı ve şifre doğrulaması yapılabilir

    String url = "jdbc:mysql://127.0.0.1:3306/fzt";
    String username = "root";
    String pass = "fsmvu";

    try (Connection conn = DriverManager.getConnection(url, username, pass)) {
        String query = "SELECT * FROM admin WHERE id = ? AND username = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, kullaniciAdi);
        statement.setString(2, sifre);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int ID = resultSet.getInt("ID");
            String username1 = resultSet.getString("username");

            AdminPage adminSayf = new AdminPage();
            Admin adminObj = new Admin();
            adminObj.setUsername(username1);
            adminSayf.getIDlabel().setText(String.valueOf(ID));
            adminSayf.getUsernameLabel().setText(username1);

            adminSayf.setVisible(true);
            JOptionPane.showMessageDialog(this, "Kullanıcı girişi başarılı.");

            // Login sayfasını kapat
            dispose();

            return true;
        } else {
            Admin adminObj = new Admin();
            if (kullaniciAdi.equals(adminObj.getUsername()) && sifre.equals(adminObj.getPassword())) {
                AdminPage adminSayf = new AdminPage();
                adminSayf.getUsernameLabel().setText(kullaniciAdi);
                adminSayf.setVisible(true);
                JOptionPane.showMessageDialog(this, "Kullanıcı girişi başarılı.");

                // Login sayfasını kapat
                dispose();

                return true;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
    }
 
}

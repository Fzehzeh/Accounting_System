/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cp2labproject;

/**
 *
 * @author dell
 */
public class Company extends CompanyPage {

    private String name;
    private String username;
    private String password;
    private int Income;
    private int Tax;
    private int ID;

    /**
     *
     * @return
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param surname the username to set
     */
    public void setUsername(String surname) {
        this.username = surname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the Income
     */
    public int getIncome() {
        return Income;
    }

    /**
     * @param income the Income to set
     */
    public void setIncome(int income) {
        this.Income = income;
    }

    /**
     * @return the Tax
     */
    public int getTax() {
        return Tax;
    }

    /**
     * @param Tax the Tax to set
     */
    public void setTax(int Tax) {
        this.Tax = Tax;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

}

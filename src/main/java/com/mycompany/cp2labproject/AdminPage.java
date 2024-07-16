/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cp2labproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dell
 */
public class AdminPage extends javax.swing.JFrame {

    public static ArrayList<CompanyPage> companylistfeature = new ArrayList<>();
    public static DefaultTableModel companymodel = new DefaultTableModel();
    public static DefaultTableModel managermodel = new DefaultTableModel();
    public static DefaultTableModel adminmodel = new DefaultTableModel();
    public static DefaultListModel taxlistmodel = new DefaultListModel();

    public JLabel getUsernameLabel() {
        return usernmelbl;
    }

    public JLabel getIDlabel() {
        return IDadmnlbl;
    }

    /**
     * Creates new form AdminPage
     */
    public AdminPage() {
        initComponents();

    }
    String url = "jdbc:mysql://localhost:3306/fzt ";
    String username = "root"; // Kullanıcı adını buraya girin
    String password = "fsmvu";

    /**
     * Bu metodun amacı, şirket verilerini veritabanından çekip, bir tablo
     * modeline yüklemektir. Şirket verileri, belirtilen sütunları içeren bir
     * tablo modeline eklenir.
     */
    private void loadCompanyData() {
        // Tablo modelini sütun tanımlarıyla oluştur

        companymodel.setColumnIdentifiers(new String[]{"id", "name", "Username", "income", " tax",});
        companymodel.setRowCount(0);

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM company")) {

// ResultSet üzerinde döngü başlat, tüm şirket verilerini al
            while (resultSet.next()) {
                // Her bir sütundan veriyi çek
                String id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String username = resultSet.getString("username");
                String income = resultSet.getString("income");
                String tax = resultSet.getString("tax");

                // Tablo modeline yeni bir satır ekle
                companymodel.addRow(new Object[]{id, name, username, income, tax});
            }

        } catch (SQLException ex) {
            // Eğer bir SQL hatası oluşursa, hatayı yazdır
            ex.printStackTrace();
        }
    }

    /**
     * Bu metodun amacı, yönetici verilerini veritabanından çekip, bir tablo
     * modeline yüklemektir. Yönetici verileri, belirtilen sütunları içeren bir
     * tablo modeline eklenir.
     */
    private void loadManagerData() {
        // Yönetici tablo modelini sütun tanımlarıyla oluştur

        managermodel.setColumnIdentifiers(new String[]{"ID", "Name", "Surname"});
        managermodel.setRowCount(0);

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT id, name, surname FROM manager")) {

            // ResultSet üzerinde döngü başlat, tüm yönetici verilerini al
            while (resultSet.next()) {
                // Her bir sütundan veriyi çek
                String id = resultSet.getString("id");
                String name = resultSet.getString("Name");
                String surname = resultSet.getString("Surname");

                // Tablo modeline yeni bir satır ekle
                managermodel.addRow(new Object[]{id, name, surname});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Bu metodun amacı, admin verilerini veritabanından çekip, bir tablo
     * modeline yüklemektir. Admin verileri, belirtilen sütunları içeren bir
     * tablo modeline eklenir.
     */
    private void loadAdminData() {
        // Admin tablo modelini sütun tanımlarıyla oluştur

        adminmodel.setColumnIdentifiers(new String[]{"ID", "Username"});
        adminmodel.setRowCount(0);

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT id, username FROM admin")) {

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String Username = resultSet.getString("Username");
                adminmodel.addRow(new Object[]{id, Username});
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Bu metodun amacı, yeni bir şirket eklemektir. Verilen parametrelerle
     * birlikte bir INSERT INTO sorgusu kullanarak yeni bir şirket eklenir.
     */
    private void addCompany(int ID, String name, String Username, int income) {
        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {
            // SQL sorgusunu oluştur
            String query = "INSERT INTO company (id, name, username, income) VALUES "
                    + "('" + ID + "', '" + name + "', '" + Username + "', " + income + ")";
            // Sorguyu execute et (veritabanına ekle)
            statement.executeUpdate(query);

        } catch (SQLException ex) {
            // Eğer bir SQL hatası oluşursa, hata türünü kontrol et

            if (ex instanceof SQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog(this, "You cannot enter the same id! Please refresh your table with the switch button!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Diğer hata türleri için hata mesajını yazdır
                ex.printStackTrace();
            }
        }
    }

    /**
     * Bu metodun amacı, yeni bir yönetici eklemektir. Verilen parametrelerle
     * birlikte bir INSERT INTO sorgusu kullanarak yeni bir yönetici eklenir.
     */
    private void addManager(int ID, String name, String surname) {
        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {

            String query = "INSERT INTO manager (id, name, surname) VALUES "
                    + "('" + ID + "', '" + name + "', '" + surname + "')";

            statement.executeUpdate(query);
        } catch (SQLException ex) {
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog(this, "You cannot enter same id! Please refresh your table with switch button!'", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Bu metodun amacı, yeni bir admin eklemektir. Verilen parametrelerle
     * birlikte bir INSERT INTO sorgusu kullanarak yeni bir admin eklenir.
     */
    private void addAdmin(int ID, String name) {
        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {

            String query = "INSERT INTO admin (id, username) VALUES "
                    + "('" + ID + "', '" + name + "')";

            statement.executeUpdate(query);
        } catch (SQLException ex) {
            if (ex instanceof SQLIntegrityConstraintViolationException) {
                JOptionPane.showMessageDialog(this, "You cannot enter same id! Please refresh your table with switch button!'", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                ex.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        companytbl = new javax.swing.JTable();
        lblList = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        surnametxt = new javax.swing.JTextPane();
        addbtn = new javax.swing.JButton();
        incometxt = new javax.swing.JTextField();
        nametxt = new javax.swing.JTextField();
        lblname = new javax.swing.JLabel();
        lblsurname = new javax.swing.JLabel();
        lblincome = new javax.swing.JLabel();
        removebtn = new javax.swing.JButton();
        lbltax = new javax.swing.JLabel();
        exitbtn = new javax.swing.JButton();
        Updatebtn = new javax.swing.JButton();
        IDtxt = new javax.swing.JTextField();
        lblid = new javax.swing.JLabel();
        compradio = new javax.swing.JRadioButton();
        mngrradio = new javax.swing.JRadioButton();
        adminradio = new javax.swing.JRadioButton();
        switchbtn = new javax.swing.JButton();
        lblAdminusername = new javax.swing.JLabel();
        usernmelbl = new javax.swing.JLabel();
        lblAdminid = new javax.swing.JLabel();
        IDadmnlbl = new javax.swing.JLabel();
        jSpinner1tax = new javax.swing.JSpinner();
        btnenter = new javax.swing.JButton();
        taxlbl = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Admin Page");

        companytbl.setBackground(new java.awt.Color(204, 204, 204));
        companytbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null"
            }
        ));
        jScrollPane1.setViewportView(companytbl);

        lblList.setBackground(new java.awt.Color(51, 0, 204));
        lblList.setFont(new java.awt.Font("Sitka Text", 1, 36)); // NOI18N
        lblList.setForeground(new java.awt.Color(102, 0, 102));
        lblList.setText("    LIST");

        jScrollPane2.setViewportView(surnametxt);

        addbtn.setText("Add");
        addbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addbtnActionPerformed(evt);
            }
        });

        lblname.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 12)); // NOI18N
        lblname.setText("Name");

        lblsurname.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblsurname.setText("Username/Surname");

        lblincome.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblincome.setText("Income");

        removebtn.setText("Remove");
        removebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removebtnActionPerformed(evt);
            }
        });

        lbltax.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lbltax.setText("Tax ");

        exitbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        exitbtn.setText("LogOut");
        exitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtnActionPerformed(evt);
            }
        });

        Updatebtn.setText("Update");
        Updatebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdatebtnActionPerformed(evt);
            }
        });

        IDtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IDtxtActionPerformed(evt);
            }
        });

        lblid.setFont(new java.awt.Font("Source Serif Pro Semibold", 1, 12)); // NOI18N
        lblid.setText("ID");

        buttonGroup6.add(compradio);
        compradio.setText("Company Table");
        compradio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compradioActionPerformed(evt);
            }
        });

        buttonGroup6.add(mngrradio);
        mngrradio.setText("Manager Table");
        mngrradio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mngrradioActionPerformed(evt);
            }
        });

        buttonGroup6.add(adminradio);
        adminradio.setText("Admin Table");

        switchbtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        switchbtn.setText("Switch - Query Table");
        switchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchbtnActionPerformed(evt);
            }
        });

        lblAdminusername.setText("Admin Username:");

        lblAdminid.setText("Admin ID");

        IDadmnlbl.setText("    ");

        jSpinner1tax.setModel(new javax.swing.SpinnerNumberModel(0, null, null, 10));

        btnenter.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnenter.setText("Save");
        btnenter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnenterActionPerformed(evt);
            }
        });

        taxlbl.setText("       ");

        label1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        label1.setText("Select the table to which you \nwant to add the information you entered.");

        jMenuBar1.setForeground(new java.awt.Color(51, 0, 204));

        jMenu1.setBackground(new java.awt.Color(204, 51, 0));
        jMenu1.setForeground(new java.awt.Color(153, 0, 153));
        jMenu1.setText("Check");

        jMenuItem1.setText("Tax Check");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblList, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(562, 562, 562)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblAdminusername)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(IDadmnlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblAdminid, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addComponent(usernmelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(exitbtn)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(429, 429, 429)
                        .addComponent(btnenter, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbltax, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblname, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblid, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblsurname)
                            .addComponent(lblincome, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(129, 129, 129)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(taxlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSpinner1tax, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(incometxt, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(nametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(IDtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(96, 96, 96)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mngrradio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(compradio, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(removebtn))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(switchbtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Updatebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(adminradio, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(212, 212, 212)))))
                .addContainerGap(518, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(exitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(usernmelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAdminid)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblname, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblsurname, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(29, 29, 29))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(31, 31, 31)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(lblincome, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(89, 105, Short.MAX_VALUE))
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(110, 110, 110)
                                        .addComponent(incometxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(37, 37, 37))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(removebtn)
                                        .addGap(45, 45, 45)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jSpinner1tax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(taxlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbltax, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(switchbtn)
                                    .addComponent(Updatebtn))
                                .addGap(18, 18, 18)
                                .addComponent(btnenter))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(55, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IDtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblid, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addComponent(nametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mngrradio)
                            .addComponent(addbtn))
                        .addGap(18, 18, 18)
                        .addComponent(adminradio)
                        .addGap(18, 18, 18)
                        .addComponent(compradio)
                        .addGap(112, 112, 112)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAdminusername)
                            .addComponent(IDadmnlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblList)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
/**
     * Bu metodun amacı, "Add" butonuna tıklandığında gerçekleşen olayı
     * yönetmektir. Kullanıcının girdiği verilere bağlı olarak, admin, şirket
     * veya yönetici eklenir. Eklenen veriler aynı zamanda tablo modeline de
     * eklenir.
     */
    private void addbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addbtnActionPerformed
        try {
            String name = nametxt.getText();
            if (name.isEmpty()) {
                // Isim boşsa hata 

                throw new NumberFormatException();
            }
            // ID String'i integer'a çevrilir
            String IDStr = IDtxt.getText();
            if (IDStr.isEmpty()) {
                throw new NumberFormatException();
            }

            int ID = Integer.parseInt(IDStr);

            if (adminradio.isSelected()) {
                // Eğer admin seçiliyse, admin eklenir ve tabloya yeni satır eklenir

                addAdmin(ID, name);
                Object[] row = {ID, name};
                adminmodel.addRow(row);
            } else if (compradio.isSelected()) {
                // Eğer şirket seçiliyse, şirket eklenir ve tabloya yeni satır eklenir

                String Username = surnametxt.getText();
                if (Username.isEmpty()) {
                    throw new NumberFormatException();
                }

                if (name.matches(".*\\d.*") || Username.matches(".*\\d.*")) {
                    // Isim veya kullanıcı adında rakam varsa hata fırlatılır

                    throw new NumberFormatException();
                }

                String incomestr = incometxt.getText();
                int income = Integer.parseInt(incomestr);
                // String taxstr = taxlbl.getText();
                //  int tax = Integer.parseInt(taxstr);

                addCompany(ID, name, Username, income);
                Object[] row = {ID, name, Username, income};
                companymodel.addRow(row);
            } else if (mngrradio.isSelected()) {
                String Surname = surnametxt.getText();
                if (Surname.isEmpty()) {
                    throw new NumberFormatException();
                }

                if (name.matches(".*\\d.*") || Surname.matches(".*\\d.*")) {
                    // Isim veya soyisimde rakam varsa hata fırlatılır

                    throw new NumberFormatException();
                }

                addManager(ID, name, Surname);
                Object[] row = {ID, name, Surname};
                managermodel.addRow(row);
            }
        } catch (NumberFormatException e) {
            // Hata durumunda kullanıcıya bilgi verilir
            JOptionPane.showMessageDialog(this, "Yanlış değer girdiniz");
        }

    }//GEN-LAST:event_addbtnActionPerformed
    /**
     * Bu metodun amacı, "Remove" butonuna tıklandığında gerçekleşen olayı
     * yönetmektir. Seçili olan tablo satırını siler ve ilgili veritabanından da
     * ilgili kaydı siler.
     */
    private void removebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removebtnActionPerformed
        int selectedrow = companytbl.getSelectedRow();
        if (selectedrow >= 0) {
            // Eğer admin seçiliyse, seçili admini siler

            Object value = companytbl.getValueAt(selectedrow, 0);
            int ID = Integer.parseInt(value.toString());

            if (adminradio.isSelected()) {
                // Veritabanından admini sil
                String usernametk = (String) companytbl.getValueAt(selectedrow, 1);
                removeAdmin(usernametk);
                adminmodel.removeRow(selectedrow);
            } else if (compradio.isSelected()) {
                // Veritabanından personeli sil
                removeCompany(ID);
                companymodel.removeRow(selectedrow);
            } else if (mngrradio.isSelected()) {
                // Veritabanından yöneticiyi sil
                removeManager(ID);
                managermodel.removeRow(selectedrow);
            }
        }

    }//GEN-LAST:event_removebtnActionPerformed
    /**
     * Bu metodun amacı, "Exit" butonuna tıklandığında gerçekleşen olayı
     * yönetmektir. Kullanıcıya çıkış yapmak isteyip istemediğini sorar ve eğer
     * "Yes" (Evet) ise giriş ekranına yönlendirir.
     */
    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed

        int YesOrNo = JOptionPane.showConfirmDialog(null, "Do you want to exit?", "LogOut", JOptionPane.YES_NO_OPTION);
        if (YesOrNo == 0) {
            // Eğer kullanıcı "Yes" (Evet) derse, giriş ekranına yönlendirir
            UserLogin loginpage = new UserLogin();
            loginpage.setVisible(true);
            dispose();

        }

    }//GEN-LAST:event_exitbtnActionPerformed

    private void removeCompany(int ID) {

        String tableName = "company";

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {

            String query = "DELETE FROM " + tableName + " WHERE id = " + ID;
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void removeManager(int ID) {
        String tableName = "manager";

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {

            String query = "DELETE FROM " + tableName + " WHERE id = " + ID;
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void removeAdmin(String Username) {
        String tableName = "admin";

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {

            String query = "DELETE FROM " + tableName + " WHERE username = '" + Username + "'";
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void UpdatebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdatebtnActionPerformed
        String idText = IDtxt.getText();
        int ID = Integer.parseInt(idText);

        String newName = nametxt.getText();
        String newSurname = surnametxt.getText();

        if (adminradio.isSelected()) {
            // Sadece ID ve yeni ad değerlerini kullanarak admini güncelle
            updateAdmin(ID, newName);

            // Diğer alanları temizle veya gerekli işlemleri yap
            incometxt.setText("");
            taxlbl.setText("");

        } else if (mngrradio.isSelected()) {
            // Sadece ID, yeni ad ve yeni soyad değerlerini kullanarak yöneticiyi güncelle
            String newManagerSurname = surnametxt.getText();
            updateManager(ID, newName, newManagerSurname);
            JOptionPane.showMessageDialog(rootPane, "Please press switch button!");

            // Diğer alanları temizle veya gerekli işlemleri yap
            incometxt.setText("");
            taxlbl.setText("");
        } else if (compradio.isSelected()) {
            // ID, yeni ad, yeni soyad, yeni gelir ve yeni vergi değerlerini kullanarak şirketi güncelle
            String newincomeText = incometxt.getText();
            int newincome = Integer.parseInt(newincomeText);

            //String newTaxText = taxlbl.getText();
            int newTax;

            int number = 0; // Varsayılan değer
            Object spinnerValue = jSpinner1tax.getValue();

            if (spinnerValue instanceof Integer) {
                number = (int) spinnerValue;
            } else if (spinnerValue instanceof String) {
                try {
                    number = Integer.parseInt((String) spinnerValue);
                } catch (NumberFormatException e) {
                    // Geçersiz bir değer varsa hata ele alınabilir
                    e.printStackTrace();
                }
            }

            taxlbl.setText(Integer.toString(number));

            JOptionPane.showMessageDialog(rootPane, "Please press switch button!");
            try {
                newTax = number;
            } catch (NumberFormatException e) {
                // Hatalı bir değer girildiğinde veya boş bir değer olduğunda hata durumunu yönetin
                newTax = 0; // Varsayılan olarak 0 atandı
            }

            updateCompany(ID, newName, newSurname, newincome, newTax);
        }
    }//GEN-LAST:event_UpdatebtnActionPerformed

    private void IDtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IDtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IDtxtActionPerformed

    private void switchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchbtnActionPerformed
        // TODO add your handling code here:
        if (compradio.isSelected()) {
            loadCompanyData();
            companytbl.setModel(companymodel);
        } else if (mngrradio.isSelected()) {
            loadManagerData();
            companytbl.setModel(managermodel);
        } else if (adminradio.isSelected()) {
            loadAdminData();
            companytbl.setModel(adminmodel);
        }

    }//GEN-LAST:event_switchbtnActionPerformed

    private void compradioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compradioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_compradioActionPerformed

    private void btnenterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnenterActionPerformed

        int number;

        number = (int) jSpinner1tax.getValue();
        taxlbl.setText(Integer.toString(number));

        String idText = IDtxt.getText();
        int id = Integer.parseInt(idText);
        int income = getCompanyIncome(id); // getCompanyIncome metodu ile firmanın income değerini alın
        id = id + 100;

        // Vergi hesaplaması
        int taxPercent = number;
        int taxAmount = income * taxPercent / 100;

        // Vergi mesajı oluşturma
        String message = "Your tax is " + taxPercent + " percent which equals to " + taxAmount;

        // Veritabanına ekleme işlemi
        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO notificationstable (id, messages) VALUES (?, ?)")) {

            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, message);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Notification added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Notification insertion failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        /*int number;
        
        number= (int) jSpinner1tax.getValue();
        taxlbl.setText(Integer.toString(number));*/
        id += 100;

    }//GEN-LAST:event_btnenterActionPerformed

    private void mngrradioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mngrradioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mngrradioActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        NotificationsPage ntf = new NotificationsPage();
        ntf.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void updateCompany(int ID, String newName, String newSurname, int newIncome, int newTax) {
        String tableName = "company";

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {

            String query = "UPDATE " + tableName + " SET name = '" + newName + "', username = '" + newSurname + "', income = " + newIncome + ", tax = " + newTax + " WHERE id = " + ID;
            statement.executeUpdate(query);

            int rowCount = companymodel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                Object idObj = companymodel.getValueAt(i, 0);

                // Check if idObj is not null before casting
                if (idObj != null) {
                    int rowID = Integer.parseInt(idObj.toString());

                    if (rowID == ID) {
                        companymodel.setValueAt(newName, i, 1);
                        companymodel.setValueAt(newSurname, i, 2);
                        companymodel.setValueAt(newIncome, i, 3);
                        companymodel.setValueAt(newTax, i, 4);
                        break;
                    }
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateAdmin(int ID, String newName) {
        String tableName = "admin";

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {

            String query = "UPDATE " + tableName + " SET username = '" + newName + "' WHERE id = " + ID;
            statement.executeUpdate(query);

            int rowCount = adminmodel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                int rowID = (int) adminmodel.getValueAt(i, 0);
                if (rowID == ID) {
                    adminmodel.setValueAt(newName, i, 1);
                    break;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateManager(int ID, String newName, String newSurname) {
        String tableName = "manager";

        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement()) {

            String query = "UPDATE " + tableName + " SET name = '" + newName + "', surname = '" + newSurname + "' WHERE id = " + ID;
            statement.executeUpdate(query);

            int rowCount = managermodel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                int rowID = (int) managermodel.getValueAt(i, 0);
                if (rowID == ID) {
                    managermodel.setValueAt(newName, i, 1);
                    managermodel.setValueAt(newSurname, i, 2);
                    break;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public JTable getAdminTable() {
        return companytbl;
    }

    public int getCompanyIncome(int id) {
        int income = 0;

        try (Connection connection = DriverManager.getConnection(url, username, password); PreparedStatement preparedStatement = connection.prepareStatement("SELECT income FROM company WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    income = (int) resultSet.getDouble("income");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return income;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminPage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminPage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IDadmnlbl;
    private javax.swing.JTextField IDtxt;
    private javax.swing.JButton Updatebtn;
    private javax.swing.JButton addbtn;
    private javax.swing.JRadioButton adminradio;
    private javax.swing.JButton btnenter;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.JTable companytbl;
    private javax.swing.JRadioButton compradio;
    private javax.swing.JButton exitbtn;
    private javax.swing.JTextField incometxt;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1tax;
    private java.awt.Label label1;
    private javax.swing.JLabel lblAdminid;
    private javax.swing.JLabel lblAdminusername;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblid;
    private javax.swing.JLabel lblincome;
    private javax.swing.JLabel lblname;
    private javax.swing.JLabel lblsurname;
    private javax.swing.JLabel lbltax;
    private javax.swing.JRadioButton mngrradio;
    private javax.swing.JTextField nametxt;
    private javax.swing.JButton removebtn;
    private javax.swing.JTextPane surnametxt;
    private javax.swing.JButton switchbtn;
    private javax.swing.JLabel taxlbl;
    private javax.swing.JLabel usernmelbl;
    // End of variables declaration//GEN-END:variables
}

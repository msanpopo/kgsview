/*
 * KGSview - KGS(ネット碁会所)用戦績表示アプリケーション
 *
 * Copyright (C) 2006 -2007 sanpo
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package ui;

import action.AddMarkAction;
import action.ChangeUserAction;
import action.DelMarkAction;
import action.FilterEditAction;
import action.GameListUpdateAction;
import action.QuitAction;
import action.SettingAction;
import app.App;
import app.Config;
import app.KgsConfig;
import app.MarkList;
import app.Resource;
import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import statistics.StatisticSet;

public class MainWindow extends javax.swing.JFrame implements TableModelListener{
    private StatisticSet sset;
    
    private GamePanel gamePanel;
    private StatisticPanel statisticPanel;
    private OpponentPanel opponentTab;
    private CustomPanel customPanel;
    private KgsGraphPanel kgsGraphPanel;
    
    public MainWindow(MarkList markList, StatisticSet sset){
        this.sset = sset;
        
        Config config = App.getInstance().getConfig();
        
        initComponents();
        
        quitMenuItem.setAction(new QuitAction(this));
        
        editFilterMenuItem.setAction(new FilterEditAction());
        optionMenuItem.setAction(new SettingAction());
        
        addUserMenuItem.setAction(new AddMarkAction(markList));
        delUserMenuItem.setAction(new DelMarkAction(markList));
        
        updateButton.setAction(new GameListUpdateAction());
        updateButton.setText("");
        
        userComboBox.setModel(markList);
        
        oldAccountCheckBox.setSelected(config.getBooleanProperty(KgsConfig.OLD_ACCOUNT));
        
        gamePanel = new GamePanel(sset.game);
        statisticPanel = new StatisticPanel(sset);
        opponentTab = new OpponentPanel(sset.opponent);
        customPanel = new CustomPanel(sset.custom);
        Graph graph = new Graph(sset.game);
        kgsGraphPanel = new KgsGraphPanel();
        
        JPanel graphPanel  = new JPanel(new BorderLayout());
        graphPanel.add(graph, BorderLayout.CENTER);
        
        sset.game.addTableModelListener(graph);
        sset.game.addTableModelListener(this);
        sset.game.addTableModelListener(kgsGraphPanel);
        
        tabbedPane.addTab(Resource.get("labelGameList"), gamePanel);
        tabbedPane.addTab(Resource.get("labelStatistic"), statisticPanel);
        tabbedPane.addTab(Resource.get("labelOpponent"), opponentTab);
        tabbedPane.addTab(Resource.get("labelCustom"), customPanel);
        tabbedPane.addTab(Resource.get("labelGraph"), graphPanel);
        tabbedPane.addTab(Resource.get("labelKgsGraph"), kgsGraphPanel);
        
        pack();
        
        int w = config.getIntProperty(KgsConfig.WINDOW_WIDTH);
        int h = config.getIntProperty(KgsConfig.WINDOW_HEIGHT);
        // System.out.println("window size:" + w + ", " + h);
        setSize(w, h);
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        Object source = e.getSource();
        
        if(source == sset.game){
            int n = sset.game.getRowCount();
            statusLabel.setText(Integer.toString(n));
            
            String user = sset.game.getUserName();
            userComboBox.getEditor().setItem(user);
        }
    }
    
    public void gamePanelRepaint(){
        gamePanel.repaint();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        updateButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        userComboBox = new javax.swing.JComboBox();
        oldAccountCheckBox = new javax.swing.JCheckBox();
        tabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JSeparator();
        quitMenuItem = new javax.swing.JMenuItem();
        toolMenu = new javax.swing.JMenu();
        editFilterMenuItem = new javax.swing.JMenuItem();
        optionMenuItem = new javax.swing.JMenuItem();
        UserMenu = new javax.swing.JMenu();
        addUserMenuItem = new javax.swing.JMenuItem();
        delUserMenuItem = new javax.swing.JMenuItem();

        setTitle("KGSView");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jToolBar1.setFloatable(false);

        updateButton.setText("Update");
        updateButton.setBorderPainted(false);
        updateButton.setFocusable(false);
        updateButton.setMaximumSize(new java.awt.Dimension(66, 66));
        updateButton.setOpaque(false);
        jToolBar1.add(updateButton);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("app/resource/Resource"); // NOI18N
        jLabel2.setText(bundle.getString("labelUserName")); // NOI18N
        jLabel2.setFocusable(false);
        jToolBar1.add(jLabel2);

        userComboBox.setEditable(true);
        userComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userComboBoxActionPerformed(evt);
            }
        });
        jToolBar1.add(userComboBox);

        oldAccountCheckBox.setText(bundle.getString("includeGuest")); // NOI18N
        oldAccountCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        oldAccountCheckBox.setFocusPainted(false);
        oldAccountCheckBox.setFocusable(false);
        oldAccountCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        oldAccountCheckBox.setOpaque(false);
        oldAccountCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                oldAccountCheckBoxItemStateChanged(evt);
            }
        });
        jToolBar1.add(oldAccountCheckBox);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 15));
        jPanel1.setPreferredSize(new java.awt.Dimension(0, 15));

        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
        );

        fileMenu.setText(bundle.getString("file")); // NOI18N
        fileMenu.add(jSeparator1);

        quitMenuItem.setText(bundle.getString("quit")); // NOI18N
        fileMenu.add(quitMenuItem);

        jMenuBar1.add(fileMenu);

        toolMenu.setText(bundle.getString("tool")); // NOI18N

        editFilterMenuItem.setText(bundle.getString("editFilter")); // NOI18N
        toolMenu.add(editFilterMenuItem);

        optionMenuItem.setText(bundle.getString("option")); // NOI18N
        toolMenu.add(optionMenuItem);

        jMenuBar1.add(toolMenu);

        UserMenu.setText(bundle.getString("user")); // NOI18N

        addUserMenuItem.setText(bundle.getString("addUser")); // NOI18N
        UserMenu.add(addUserMenuItem);

        delUserMenuItem.setText(bundle.getString("delUser")); // NOI18N
        UserMenu.add(delUserMenuItem);

        jMenuBar1.add(UserMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void oldAccountCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_oldAccountCheckBoxItemStateChanged
        Config config = App.getInstance().getConfig();
        if (evt.getStateChange() == ItemEvent.DESELECTED){
            System.out.println("CheckBox : deselected");
            config.setBooleanProperty(KgsConfig.OLD_ACCOUNT, false);
        }else{
            System.out.println("CheckBox : selected");
            config.setBooleanProperty(KgsConfig.OLD_ACCOUNT, true);
        }
    }//GEN-LAST:event_oldAccountCheckBoxItemStateChanged
    
    private void userComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userComboBoxActionPerformed
        String cmd = evt.getActionCommand();
        System.out.println("userComboBox:" + cmd + ":" + evt);
        
        String newUser = "";
        
        if(cmd.equals("comboBoxEdited")){           // 入力した時だけ出る
            System.out.println("userComboBox:comboBoxEdited:" + newUser + ":" + evt);
        }else if(cmd.equals("comboBoxChanged")){    // コンボから選んだり入力したりしたら出る
            newUser = (String)userComboBox.getSelectedItem();
            
            System.out.println("userComboBox:comboBoxChanged:" + newUser + ":" + evt);
        }
        
        ChangeUserAction action = new ChangeUserAction(newUser);
        action.doAction();
    }//GEN-LAST:event_userComboBoxActionPerformed
    
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        System.out.println("MainWindow.Closed");
        App.getInstance().stop();
    }//GEN-LAST:event_formWindowClosed
    
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        int w = evt.getComponent().getWidth();
        int h = evt.getComponent().getHeight();
        
        // System.out.println("componentResized:" + w + ", " + h);
        
        Config config = App.getInstance().getConfig();
        config.setIntProperty(KgsConfig.WINDOW_WIDTH, w);
        config.setIntProperty(KgsConfig.WINDOW_HEIGHT, h);
    }//GEN-LAST:event_formComponentResized
    
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        System.out.println("componentHidden");
        dispose();
    }//GEN-LAST:event_formComponentHidden
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu UserMenu;
    private javax.swing.JMenuItem addUserMenuItem;
    private javax.swing.JMenuItem delUserMenuItem;
    private javax.swing.JMenuItem editFilterMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JCheckBox oldAccountCheckBox;
    private javax.swing.JMenuItem optionMenuItem;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JMenu toolMenu;
    private javax.swing.JButton updateButton;
    private javax.swing.JComboBox userComboBox;
    // End of variables declaration//GEN-END:variables
}
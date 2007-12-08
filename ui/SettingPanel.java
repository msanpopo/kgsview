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

import app.App;
import app.Config;
import app.KgsConfig;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class SettingPanel extends javax.swing.JPanel{
    private Config config;
    private DefaultComboBoxModel model;
    
    private HashMap<String, LookAndFeelInfo> lookFeelMap;
    
    public SettingPanel() {
        this.config = App.getInstance().getConfig();
        model = new DefaultComboBoxModel();
        
        String currentLookAndFeel = UIManager.getLookAndFeel().getClass().toString() ;
        System.out.println("laf:" + currentLookAndFeel);
        
        lookFeelMap = new HashMap<String, LookAndFeelInfo>();
        
        LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
        for(LookAndFeelInfo i : info){
            System.out.println(i.getName() + ":" + i.getClassName());
            model.addElement(i.getName());
            lookFeelMap.put(i.getName(), i);
        }
        
        initComponents();
        
        lookAndFeelComboBox.setModel(model);
        
        load();
    }
    
    private void setLookAndFeel(String lookAndFeelClassName){
        try {
            UIManager.setLookAndFeel(lookAndFeelClassName);
            SwingUtilities.updateComponentTreeUI(App.getInstance().getMainWindow());
            if(this.getTopLevelAncestor() != null){
                SwingUtilities.updateComponentTreeUI(this.getTopLevelAncestor());
            }
        } catch (Exception ex) {
            System.err.println("Ui UIManager.setLookAndFeel:" + lookAndFeelClassName);
            ex.printStackTrace();
        }
    }
    
    private void load(){
        commandField.setText(config.getProperty(KgsConfig.SGF_APP_PATH));
        
        String lf = config.getProperty(KgsConfig.LOOK_AND_FEEL);
        for(LookAndFeelInfo i : lookFeelMap.values()){
            if(lf.equals(i.getClassName())){
                
                model.setSelectedItem(i.getName());
                break;
            }
        }
        
        timeZoneTextField.setText(config.getProperty(KgsConfig.TIME_ZONE));
    }
    
    private void save(){
        System.out.println("SettingPanel.save:  config:" + config);
        System.out.println("command :" + commandField.getText());
        System.out.println("timezone:" + timeZoneTextField.getText());
        
        config.setProperty(KgsConfig.SGF_APP_PATH, commandField.getText());
        
        String lf = (String)model.getSelectedItem();
        LookAndFeelInfo info = lookFeelMap.get(lf);
        config.setProperty(KgsConfig.LOOK_AND_FEEL, info.getClassName());
        
        config.setProperty(KgsConfig.TIME_ZONE, timeZoneTextField.getText());
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        sgsViewerPanel = new javax.swing.JPanel();
        commandLabel = new javax.swing.JLabel();
        commandField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lookAndFeelComboBox = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        timeZoneTextField = new javax.swing.JTextField();

        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                formAncestorRemoved(evt);
            }
        });

        sgsViewerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("sgfViewer")));
        commandLabel.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("commandLabel"));

        javax.swing.GroupLayout sgsViewerPanelLayout = new javax.swing.GroupLayout(sgsViewerPanel);
        sgsViewerPanel.setLayout(sgsViewerPanelLayout);
        sgsViewerPanelLayout.setHorizontalGroup(
            sgsViewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sgsViewerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(commandLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commandField, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );
        sgsViewerPanelLayout.setVerticalGroup(
            sgsViewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sgsViewerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sgsViewerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(commandLabel)
                    .addComponent(commandField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("lookAndFeel")));
        lookAndFeelComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lookAndFeelComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lookAndFeelComboBox, 0, 366, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lookAndFeelComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("timeZone")));
        timeZoneTextField.setText("jTextField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeZoneTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(timeZoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sgsViewerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sgsViewerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorRemoved
        System.out.println("SettingPanel.formAncestorRemoved");
        save();
    }//GEN-LAST:event_formAncestorRemoved
    
    private void lookAndFeelComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lookAndFeelComboBoxActionPerformed
        String lf = (String)model.getSelectedItem();
        System.out.println("lookAndFeelComboBoxActionPerformed:" + lf);
        LookAndFeelInfo info = lookFeelMap.get(lf);
        setLookAndFeel(info.getClassName());
    }//GEN-LAST:event_lookAndFeelComboBoxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField commandField;
    private javax.swing.JLabel commandLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JComboBox lookAndFeelComboBox;
    private javax.swing.JPanel sgsViewerPanel;
    private javax.swing.JTextField timeZoneTextField;
    // End of variables declaration//GEN-END:variables
}
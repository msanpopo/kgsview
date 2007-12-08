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

import action.FilterEditAction;
import app.App;
import app.Config;
import app.KgsConfig;
import app.Resource;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import statistics.filter.FilterList;

public class FilterSelectPanel extends javax.swing.JPanel{
    private static final String NO_FILTER = Resource.get("noFilter");
    
    private Config config;
    private FilterList filterList;
    
    private DefaultComboBoxModel gameListModel;
    private DefaultComboBoxModel typeModel;
    private DefaultComboBoxModel setupModel;
    private DefaultComboBoxModel recordModel;
    private DefaultComboBoxModel monthModel;
    private DefaultComboBoxModel opponentModel;
    
    public FilterSelectPanel() {
        config = App.getInstance().getConfig();
        filterList = App.getInstance().getFilterList();
        
        gameListModel= new DefaultComboBoxModel();
        typeModel= new DefaultComboBoxModel();
        setupModel= new DefaultComboBoxModel();
        recordModel= new DefaultComboBoxModel();
        monthModel= new DefaultComboBoxModel();
        opponentModel= new DefaultComboBoxModel();
        
        initComponents();
        
        load();
        
        gameListCombo.setModel(gameListModel);
        typeCombo.setModel(typeModel);
        setupCombo.setModel(setupModel);
        recordCombo.setModel(recordModel);
        monthCombo.setModel(monthModel);
        opponentCombo.setModel(opponentModel);
    }
    
    private void load(){
        gameListModel.removeAllElements();
        typeModel.removeAllElements();
        setupModel.removeAllElements();
        recordModel.removeAllElements();
        monthModel.removeAllElements();
        opponentModel.removeAllElements();
        
        gameListModel.addElement(NO_FILTER);
        typeModel.addElement(NO_FILTER);
        setupModel.addElement(NO_FILTER);
        recordModel.addElement(NO_FILTER);
        monthModel.addElement(NO_FILTER);
        opponentModel.addElement(NO_FILTER);
        
        for(String name : filterList.getNameArray()){
            gameListModel.addElement(name);
            typeModel.addElement(name);
            setupModel.addElement(name);
            recordModel.addElement(name);
            monthModel.addElement(name);
            opponentModel.addElement(name);
        }
        
        loadFilter(KgsConfig.FILTER_GAME_LIST, gameListModel);
        loadFilter(KgsConfig.FILTER_TYPE, typeModel);
        loadFilter(KgsConfig.FILTER_SETUP, setupModel);
        loadFilter(KgsConfig.FILTER_RECORD, recordModel);
        loadFilter(KgsConfig.FILTER_MONTH, monthModel);
        loadFilter(KgsConfig.FILTER_OPPONENT, opponentModel);
    }
    
    private void loadFilter(KgsConfig kgsConf, ComboBoxModel model){
        String name = config.getProperty(kgsConf);
        
        if(name == null){
            name = NO_FILTER;
        }else if(name.isEmpty()){
            name = NO_FILTER;
        }else if(name.equals(NO_FILTER)){
            name = NO_FILTER;
        }
        model.setSelectedItem(name);
    }
    
    private void save(){
        saveFilter(KgsConfig.FILTER_GAME_LIST, gameListModel);
        saveFilter(KgsConfig.FILTER_TYPE, typeModel);
        saveFilter(KgsConfig.FILTER_SETUP, setupModel);
        saveFilter(KgsConfig.FILTER_RECORD, recordModel);
        saveFilter(KgsConfig.FILTER_MONTH, monthModel);
        saveFilter(KgsConfig.FILTER_OPPONENT, opponentModel);
    }
    
    private void saveFilter(KgsConfig kgsConf, ComboBoxModel model){
        String name = (String)model.getSelectedItem();
        if(name == null){
            name = NO_FILTER;
        }else if(name != null && name.isEmpty()){
            name = NO_FILTER;
        }else if(name.equals(NO_FILTER)){
            name = NO_FILTER;
        }
        
        config.setProperty(kgsConf, name);
    } 
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        gameListCombo = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        typeCombo = new javax.swing.JComboBox();
        setupCombo = new javax.swing.JComboBox();
        recordCombo = new javax.swing.JComboBox();
        monthCombo = new javax.swing.JComboBox();
        opponentCombo = new javax.swing.JComboBox();
        editButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
                formAncestorRemoved(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("labelGameList"));
        jLabel1.setAutoscrolls(true);

        gameListCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("tableType"));
        jLabel2.setAutoscrolls(true);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("tableSetup"));
        jLabel3.setAutoscrolls(true);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("tableRecord"));
        jLabel4.setAutoscrolls(true);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("tableMonth"));
        jLabel5.setAutoscrolls(true);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("labelOpponent"));
        jLabel6.setAutoscrolls(true);

        typeCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setupCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        recordCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        monthCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        opponentCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        editButton.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("editFilter"));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gameListCombo, 0, 270, Short.MAX_VALUE)
                            .addComponent(typeCombo, 0, 270, Short.MAX_VALUE)
                            .addComponent(setupCombo, 0, 270, Short.MAX_VALUE)
                            .addComponent(recordCombo, 0, 270, Short.MAX_VALUE)
                            .addComponent(opponentCombo, 0, 270, Short.MAX_VALUE)
                            .addComponent(monthCombo, 0, 270, Short.MAX_VALUE)))
                    .addComponent(editButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gameListCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setupCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recordCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(monthCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(opponentCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void formAncestorRemoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorRemoved
        System.out.println("FilterSelectPanel.formAncestorRemoved");
        save();
        App.getInstance().filterSelectionChanged();
    }//GEN-LAST:event_formAncestorRemoved
    
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        System.out.println("FilterSelectedPanel.editButtonAction");
        FilterEditAction action = new FilterEditAction();
        action.doAction();
        load();
    }//GEN-LAST:event_editButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton editButton;
    private javax.swing.JComboBox gameListCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox monthCombo;
    private javax.swing.JComboBox opponentCombo;
    private javax.swing.JComboBox recordCombo;
    private javax.swing.JComboBox setupCombo;
    private javax.swing.JComboBox typeCombo;
    // End of variables declaration//GEN-END:variables
}
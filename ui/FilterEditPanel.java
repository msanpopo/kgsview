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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import statistics.filter.AndFilter;
import statistics.filter.DateFilter;
import statistics.filter.GameFilter;
import statistics.filter.TypeFilter;
import statistics.filter.ui.DateFilterPanel;
import statistics.filter.ui.TypeFilterPanel;

public class FilterEditPanel extends javax.swing.JPanel {
    private TypeFilterPanel typeFilterPanel;
    private DateFilterPanel dateFilterPanel;
    
    public FilterEditPanel(GameFilter filter){
        initComponents();
        
        typeFilterPanel = new TypeFilterPanel();
        dateFilterPanel = new DateFilterPanel();
        
        typePanelBase.setLayout(new BorderLayout());
        typePanelBase.add(typeFilterPanel);
        datePanelBase.setLayout(new BorderLayout());
        datePanelBase.add(dateFilterPanel);

        setEnabledLocal(typePanelBase, false);
        setEnabledLocal(datePanelBase, false);
        
        setFilter(filter);
    }
    
    private void setFilter(GameFilter filter){
        if(filter == null){
            return;
        }
        System.out.println("FilterEditPanel.setFilter:" + filter.getName());
        filterNameTextField.setText(filter.getName());
        
        setFilterChild(filter);
    }
    
    private void setFilterChild(GameFilter filter){
        if(filter instanceof TypeFilter){
            typeCheckBox.setSelected(true);
            typeFilterPanel.setTypeFilter((TypeFilter)filter);
        }else if(filter instanceof DateFilter){
            dateCheckBox.setSelected(true);
            dateFilterPanel.setDateFilter((DateFilter)filter);
        }else if(filter instanceof AndFilter){
            // TODO AndFilter のような複合のフィルターがネストしてると正しく扱えない
            AndFilter af = (AndFilter)filter;
            Collection<GameFilter> c = af.getCollection();
            for(GameFilter f : c){
                setFilterChild(f);
            }
        }else{
            System.err.println("error:FilterEditPanel.setFilter:" + filter.getClass().getName());
        }
    }
    
    public GameFilter getFilter(){
        ArrayList<GameFilter> list = new ArrayList<GameFilter>();
        
        if(typeCheckBox.isSelected()){
            list.add(typeFilterPanel.getTypeFilter());
        }
        
        if(dateCheckBox.isSelected()){
            list.add(dateFilterPanel.getDateFilter());
        }
        
        
        GameFilter filter = null;
        if(list.size() == 0){
            
        }else if(list.size() == 1){
            filter = list.get(0);
        }else{
            AndFilter andFilter = new AndFilter();
            for(GameFilter f : list){
                andFilter.add(f);
            }
            filter = andFilter;
        }
        
        if(filter != null){
            filter.setName(filterNameTextField.getText());
            
            System.out.println("FilterEditPanel.getFilter:" + filter.toString());
        }

        return filter;
    }
    
    private static void setEnabledLocal(Component component, boolean bool){
        component.setEnabled(bool);
        if(component instanceof Container){
            Container con = (Container)component;
 
            for(Component c : con.getComponents()){
                setEnabledLocal(c, bool);
            }
        }
    }
    
    private int count(){
        int n = 0;
        if(typeCheckBox.isSelected()){
            n += 1;
        }
        
        if(dateCheckBox.isSelected()){
            n += 1;
        }
        System.out.println("FilterEditPanel.count:" + n);
        return n;
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        datePanelBase = new javax.swing.JPanel();
        typePanelBase = new javax.swing.JPanel();
        typeCheckBox = new javax.swing.JCheckBox();
        filterNameTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        dateCheckBox = new javax.swing.JCheckBox();

        datePanelBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        javax.swing.GroupLayout datePanelBaseLayout = new javax.swing.GroupLayout(datePanelBase);
        datePanelBase.setLayout(datePanelBaseLayout);
        datePanelBaseLayout.setHorizontalGroup(
            datePanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );
        datePanelBaseLayout.setVerticalGroup(
            datePanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );

        typePanelBase.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        javax.swing.GroupLayout typePanelBaseLayout = new javax.swing.GroupLayout(typePanelBase);
        typePanelBase.setLayout(typePanelBaseLayout);
        typePanelBaseLayout.setHorizontalGroup(
            typePanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 223, Short.MAX_VALUE)
        );
        typePanelBaseLayout.setVerticalGroup(
            typePanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );

        typeCheckBox.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("tableType"));
        typeCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        typeCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        typeCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                typeCheckBoxItemStateChanged(evt);
            }
        });

        jLabel1.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("filterName"));

        dateCheckBox.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("date"));
        dateCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        dateCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        dateCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dateCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filterNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(typeCheckBox)
                            .addComponent(typePanelBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dateCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 221, Short.MAX_VALUE))
                            .addComponent(datePanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(filterNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeCheckBox)
                    .addComponent(dateCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(datePanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(typePanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dateCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_dateCheckBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.DESELECTED){
            setEnabledLocal(datePanelBase, false);
        }else{
            setEnabledLocal(datePanelBase, true);
        }
    }//GEN-LAST:event_dateCheckBoxItemStateChanged

    private void typeCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_typeCheckBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.DESELECTED){
            setEnabledLocal(typePanelBase, false);
        }else{
            setEnabledLocal(typePanelBase, true);
        }
    }//GEN-LAST:event_typeCheckBoxItemStateChanged
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox dateCheckBox;
    private javax.swing.JPanel datePanelBase;
    private javax.swing.JTextField filterNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox typeCheckBox;
    private javax.swing.JPanel typePanelBase;
    // End of variables declaration//GEN-END:variables
}
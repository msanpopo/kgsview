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

package statistics.filter.ui;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import statistics.filter.DateFilter;

public class DateFilterPanel extends javax.swing.JPanel {
    private DateFilter filter;
    
    private SpinnerDateModel firstModel;
    private SpinnerDateModel lastModel;
    private SpinnerDateModel beforeModel;
    private SpinnerDateModel afterModel;
    private SpinnerNumberModel daysModel;
    
    public DateFilterPanel() {
        filter = new DateFilter();
        
        firstModel = new SpinnerDateModel();
        lastModel = new SpinnerDateModel();
        beforeModel = new SpinnerDateModel();
        afterModel = new SpinnerDateModel();
        daysModel = new SpinnerNumberModel(1, 0, 3000, 1);
        
        initComponents();
        
        firstSpinner.setModel(firstModel);
        lastSpinner.setModel(lastModel);
        beforeSpinner.setModel(beforeModel);
        afterSpinner.setModel(afterModel);
        daysSpinner.setModel(daysModel);
        
        firstSpinner.setEditor(new JSpinner.DateEditor(firstSpinner, "yyyy/MM/dd"));
        lastSpinner.setEditor(new JSpinner.DateEditor(lastSpinner, "yyyy/MM/dd"));
        beforeSpinner.setEditor(new JSpinner.DateEditor(beforeSpinner, "yyyy/MM/dd"));
        afterSpinner.setEditor(new JSpinner.DateEditor(afterSpinner, "yyyy/MM/dd"));
        
        setDateFilter(filter);
    }
    
    public void setDateFilter(DateFilter filter){
        this.filter = filter;
        
        int type = filter.getType();
        
        //System.out.println("#######" + filter.toString());
        switch(type){
            case DateFilter.TYPE_SPAN:
                firstModel.setValue(filter.getFirst());
                lastModel.setValue(filter.getLast());
                spanRadioButton.setSelected(true);
                break;
            case DateFilter.TYPE_BEFORE:
                beforeModel.setValue(filter.getBefore());
                beforeRadioButton.setSelected(true);
                break;
            case DateFilter.TYPE_AFTER:
                afterModel.setValue(filter.getAfter());
                afterRadioButton.setSelected(true);
                break;
            case DateFilter.TYPE_LAST:
                daysModel.setValue(filter.getDays());
                lastRadioButton.setSelected(true);
                break;
            default:
                System.err.println("error:DateFilterPanel.setDateFilter():" + filter);
                break;
        }
    }
    
    public DateFilter getDateFilter(){
        int type;
        if(spanRadioButton.isSelected()){
            type = DateFilter.TYPE_SPAN;
        }else if(beforeRadioButton.isSelected()){
            type = DateFilter.TYPE_BEFORE;
        }else if(afterRadioButton.isSelected()){
            type = DateFilter.TYPE_AFTER;
        }else{
            type = DateFilter.TYPE_LAST;
        }
        
        switch(type){
            case DateFilter.TYPE_SPAN:
                filter.setSpan(firstModel.getDate(), lastModel.getDate());
                break;
            case DateFilter.TYPE_BEFORE:
                filter.setBefore(beforeModel.getDate());
                break;
            case DateFilter.TYPE_AFTER:
                filter.setAfter(afterModel.getDate());
                break;
            case DateFilter.TYPE_LAST:
                filter.setLast((Integer)daysModel.getValue());
                break;
            default:
                System.err.println("error:DateFilterPanel.getDateFilter():" + filter);
                break;
        }
        
        return filter;
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup = new javax.swing.ButtonGroup();
        spanRadioButton = new javax.swing.JRadioButton();
        beforeRadioButton = new javax.swing.JRadioButton();
        afterRadioButton = new javax.swing.JRadioButton();
        lastRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        afterSpinner = new javax.swing.JSpinner();
        beforeSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        firstSpinner = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        lastSpinner = new javax.swing.JSpinner();
        daysSpinner = new javax.swing.JSpinner();

        buttonGroup.add(spanRadioButton);
        spanRadioButton.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("fromTo"));
        spanRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        spanRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        spanRadioButton.setMaximumSize(new java.awt.Dimension(70, 15));
        spanRadioButton.setMinimumSize(new java.awt.Dimension(70, 15));
        spanRadioButton.setPreferredSize(new java.awt.Dimension(70, 15));

        buttonGroup.add(beforeRadioButton);
        beforeRadioButton.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("to"));
        beforeRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        beforeRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        beforeRadioButton.setMaximumSize(new java.awt.Dimension(70, 15));
        beforeRadioButton.setMinimumSize(new java.awt.Dimension(70, 15));
        beforeRadioButton.setPreferredSize(new java.awt.Dimension(70, 15));

        buttonGroup.add(afterRadioButton);
        afterRadioButton.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("from"));
        afterRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        afterRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        afterRadioButton.setMaximumSize(new java.awt.Dimension(70, 15));
        afterRadioButton.setMinimumSize(new java.awt.Dimension(70, 15));
        afterRadioButton.setPreferredSize(new java.awt.Dimension(70, 15));

        buttonGroup.add(lastRadioButton);
        lastRadioButton.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("last"));
        lastRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        lastRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        lastRadioButton.setMaximumSize(new java.awt.Dimension(70, 15));
        lastRadioButton.setMinimumSize(new java.awt.Dimension(70, 15));
        lastRadioButton.setPreferredSize(new java.awt.Dimension(70, 15));

        jLabel1.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("days"));

        jLabel2.setText("\u301c");

        afterSpinner.setPreferredSize(new java.awt.Dimension(110, 20));

        beforeSpinner.setPreferredSize(new java.awt.Dimension(110, 20));

        jLabel3.setText("\u301c");

        firstSpinner.setPreferredSize(new java.awt.Dimension(110, 20));

        jLabel4.setText("\u301c");

        lastSpinner.setPreferredSize(new java.awt.Dimension(110, 20));

        daysSpinner.setPreferredSize(new java.awt.Dimension(110, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(spanRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(firstSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(beforeRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(beforeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lastSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(afterRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(afterSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lastRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(daysSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spanRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(lastSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(beforeRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(beforeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(afterRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(afterSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(daysSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton afterRadioButton;
    private javax.swing.JSpinner afterSpinner;
    private javax.swing.JRadioButton beforeRadioButton;
    private javax.swing.JSpinner beforeSpinner;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JSpinner daysSpinner;
    private javax.swing.JSpinner firstSpinner;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JRadioButton lastRadioButton;
    private javax.swing.JSpinner lastSpinner;
    private javax.swing.JRadioButton spanRadioButton;
    // End of variables declaration//GEN-END:variables
}
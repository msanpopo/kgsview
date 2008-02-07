/*
 * KGSview - KGS(ネット碁会所)用戦績表示アプリケーション
 *
 * Copyright (C) 2008 sanpo
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

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CalendarRowPanel extends javax.swing.JPanel {
    
    public CalendarRowPanel(){
        init();
    }
    
    public CalendarRowPanel(boolean header) {
        
        init();        
        
        yearLabel.setText("");
        if(header){
            for(int i = 0; i < 12; ++i){
                JLabel label = createLabel30(String.valueOf(i + 1));
                
                monthPanel.add(label);
            }
        }
    }
    
    private void init(){
        initComponents();
    }
    
    public void setYearLabel(String label){
        yearLabel.setText(label);
    }
    
    public void addCheckBox(int year, int month, ActionListener listener){
        StringBuilder str = new StringBuilder();
        str.append(year);
        str.append("-");
        str.append(month);
        
        JCheckBox checkBox = createCheckBox30();
        checkBox.setActionCommand(str.toString());
        checkBox.addActionListener(listener);
        
        monthPanel.add(checkBox);
    }
    
    public void addLabel(String text){
        JLabel label = createLabel30(text);
        
        monthPanel.add(label);
    }
    
    private JLabel createLabel30(String text){
        JLabel label = new JLabel(text);
        label.setMaximumSize(new Dimension(30, 20));
        label.setMinimumSize(new Dimension(30, 20));
        label.setPreferredSize(new Dimension(30, 20));
//        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
//        label.setVerticalTextPosition(SwingConstants.CENTER);
        return label;
    }
    
    private JCheckBox createCheckBox30(){
        JCheckBox checkBox = new JCheckBox();
        checkBox.setMaximumSize(new Dimension(30, 20));
        checkBox.setMinimumSize(new Dimension(30, 20));
        checkBox.setPreferredSize(new Dimension(30, 20));
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        checkBox.setVerticalAlignment(SwingConstants.CENTER);
        return checkBox;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        yearLabel = new javax.swing.JLabel();
        monthPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();

        setMaximumSize(new java.awt.Dimension(32767, 30));
        setMinimumSize(new java.awt.Dimension(0, 30));

        yearLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yearLabel.setText("0000");

        monthPanel.setLayout(new javax.swing.BoxLayout(monthPanel, javax.swing.BoxLayout.LINE_AXIS));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yearLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(yearLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(monthPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel monthPanel;
    private javax.swing.JLabel yearLabel;
    // End of variables declaration//GEN-END:variables
}
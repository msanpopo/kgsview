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

import archive.DownloadState;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import statistics.CalendarTable;

public class CalendarRowPanel extends JPanel implements ActionListener{

    private final Color GREEN = new Color(0xcc, 0xff, 0xcc);
    private final Color YELLOW = new Color(0xff, 0xff, 0xcc);
    private final Color RED = new Color(0xff, 0xcc, 0xcc);
        
    private static final int BOX_WIDTH = 40;
    private static final int BOX_HEIGHT = 30;
    
    private CalendarTable table;
    private int year;
    
    public CalendarRowPanel(CalendarTable table){
        init(table, 0);
    }
    
    public CalendarRowPanel(CalendarTable table, int year){
        init(table, year);
        
        for (int m = 1; m <= 12; ++m) {
            if (table.hasCalendar(year, m)) {
                DownloadState state = table.getDownloadState(year, m);
                boolean mark = table.getDownloadMark(year, m);
                addCheckBox(m, state, mark);
            } else {
                addLabel("");
            }
        }
    }
    
    private void init(CalendarTable table, int year){
        this.table = table;
        this.year = year;

        initComponents();
        
        if(year != 0){
            JCheckBox checkBox = new JCheckBox();
            checkBox.setMaximumSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
            checkBox.setMinimumSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
            checkBox.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
            checkBox.setActionCommand(Integer.toString(year));
            checkBox.addActionListener(this);
            JLabel label = new JLabel(Integer.toString(year));
            yearPanel.add(checkBox);
            yearPanel.add(label);
        }
    }
    
    private void addCheckBox(int month, DownloadState state, boolean mark){
        JCheckBox checkBox = createCheckBox30();
        checkBox.setActionCommand(Integer.toString(month));
        checkBox.addActionListener(this);
        checkBox.setSelected(mark);

        Color color;
        switch(state){
            case NOT_DOWNLOADED:
                color = RED;
                break;
            case DOWNLOADED_COMPLETELY:
                color = GREEN;
                break;
            case DOWNLOADED_INCOMPLETELY:
                color = YELLOW;
                break;
            default:
                color = Color.RED;
                break;
        }
        checkBox.setBackground(color);
        
        monthPanel.add(checkBox);
    }
    
    public void addLabel(String text){
        JLabel label = createLabel30(text);
        
        monthPanel.add(label);
    }
    
    private JLabel createLabel30(String text){
        JLabel label = new JLabel(text);
        label.setMaximumSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        label.setMinimumSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        label.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
//        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
//        label.setVerticalTextPosition(SwingConstants.CENTER);
        return label;
    }
    
    private JCheckBox createCheckBox30(){
        JCheckBox checkBox = new JCheckBox();
        checkBox.setMaximumSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        checkBox.setMinimumSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        checkBox.setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
        checkBox.setHorizontalAlignment(SwingConstants.CENTER);
        checkBox.setVerticalAlignment(SwingConstants.CENTER);
        return checkBox;
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Object obj = e.getSource();
        if(command != null && command.isEmpty() == false && obj instanceof JCheckBox){
            JCheckBox checkBox = (JCheckBox)obj;
            boolean selected = checkBox.isSelected();
            
            if(command.equals(Integer.toString(year))){
                for(Component c : monthPanel.getComponents()){
                    c.setEnabled(!selected);
                    if(c instanceof JCheckBox){
                        JCheckBox cb = (JCheckBox)c;
                        cb.setSelected(selected);
                    }
                }
                for(int i = 1; i <= 12; ++i){
                    table.setDownloadMark(year, i, selected);
                }
                
            }else{
                int month = Integer.parseInt(command);

                table.setDownloadMark(year, month, selected);
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        monthPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        yearPanel = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setMaximumSize(new java.awt.Dimension(32767, 30));
        setMinimumSize(new java.awt.Dimension(0, 30));

        monthPanel.setLayout(new javax.swing.BoxLayout(monthPanel, javax.swing.BoxLayout.LINE_AXIS));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        yearPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        yearPanel.setPreferredSize(new java.awt.Dimension(0, 0));
        yearPanel.setLayout(new javax.swing.BoxLayout(yearPanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(yearPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(monthPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(monthPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
            .addComponent(yearPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel monthPanel;
    private javax.swing.JPanel yearPanel;
    // End of variables declaration//GEN-END:variables
}
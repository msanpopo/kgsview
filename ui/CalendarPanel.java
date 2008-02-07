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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import statistics.CalendarTable;
import statistics.CalendarTableListener;

public class CalendarPanel extends JPanel implements CalendarTableListener, ActionListener{
    private CalendarTable table;
    
    private JPanel header;
    
    public CalendarPanel(CalendarTable table) {
        this.table = table;
        this.table.addCalendarTableListener(this);
        
        this.header = new CalendarRowPanel(true);
                
        initComponents();

        rebuild(table);
    }

    public void calendarTableChanged(CalendarTable table) {
        rebuild(table);
    }
    
    private void rebuild(CalendarTable table){
        vPanel.removeAll();

        vPanel.add(header);
        
        if(table.hasCalendar() == false){
            return;
        }
        
        int firstYear = table.getFirstYear();
        int firstMonth = table.getFirstMonth();
        int lastYear = table.getLastYear();
        int lastMonth = table.getLastMonth();
        
        System.out.println("aaa:" + firstYear + ":" + firstMonth);
        System.out.println("aaa:" + lastYear + ":" + lastMonth);
        
        for(int y = firstYear; y <= lastYear; ++y){
            CalendarRowPanel row = new CalendarRowPanel();
            row.setYearLabel(String.valueOf(y));
            
            for(int m = 1; m <= 12; ++m){
                if(table.hasCalendar(y, m)){
                    System.out.println("zz y:" + y + ":" + m);
                    row.addCheckBox(y, m, this);
                }else{
                    row.addLabel("");
                }
            }
            JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
            sep.setMaximumSize(new Dimension(10000, 3));
            sep.setMinimumSize(new Dimension(1, 3));
            sep.setPreferredSize(new Dimension(10000, 3));
            
            
            vPanel.add(sep);
            vPanel.add(row);
        }
        
        repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Object obj = e.getSource();
        if(command != null && command.isEmpty() == false && obj instanceof JCheckBox){
            String[] a = command.split("-");
            JCheckBox checkBox = (JCheckBox)obj;
            if(a.length == 2){
                int year = Integer.parseInt(a[0]);
                int month = Integer.parseInt(a[1]);

                table.setDownloadMark(year, month, checkBox.isSelected());
            }
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vPanel = new javax.swing.JPanel();

        vPanel.setLayout(new javax.swing.BoxLayout(vPanel, javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel vPanel;
    // End of variables declaration//GEN-END:variables
}
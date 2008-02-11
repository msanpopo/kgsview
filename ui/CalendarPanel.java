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
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import statistics.CalendarTable;
import statistics.CalendarTableListener;

public class CalendarPanel extends JPanel implements CalendarTableListener{
    private CalendarTable table;
    
    private CalendarRowPanel header;
    
    public CalendarPanel(CalendarTable table) {
        this.table = table;
        this.table.addCalendarTableListener(this);
        
        this.header = new CalendarRowPanel(table);
        for(int m = 1; m <= 12; ++m){
            this.header.addLabel(String.valueOf(m));
        }
        
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
        
//        System.out.println("CalendarPanel:" + firstYear + ":" + firstMonth);
//        System.out.println("CalendarPanel:" + lastYear + ":" + lastMonth);
        
        for(int y = firstYear; y <= lastYear; ++y){
            CalendarRowPanel row = new CalendarRowPanel(table, y);
            
            JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
            sep.setMaximumSize(new Dimension(10000, 2));
            sep.setMinimumSize(new Dimension(1, 2));
            sep.setPreferredSize(new Dimension(10000, 2));
            
            vPanel.add(sep);
            vPanel.add(row);
        }
        
        repaint();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        vPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        vPanel.setLayout(new javax.swing.BoxLayout(vPanel, javax.swing.BoxLayout.Y_AXIS));

        jButton1.setText("jButton1");

        jButton2.setText("jButton2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel vPanel;
    // End of variables declaration//GEN-END:variables
}
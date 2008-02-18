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

import action.DownloadAction;
import app.Resource;
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

        downloadButton.setText(Resource.get("download"));
        downloadButton.setAction(new DownloadAction());
        
        rebuild(table);
    }

    public void calendarTableChanged(CalendarTable table) {
        this.table = table;
        rebuild(table);
    }
    
    private void rebuild(CalendarTable table){
        vPanel.removeAll();

        vPanel.add(header);
        vPanel.add(createSeparator());
        
        if(table.hasCalendar() == false){
            return;
        }
        
        int firstYear = table.getFirstYear();
        int firstMonth = table.getFirstMonth();
        int lastYear = table.getLastYear();
        int lastMonth = table.getLastMonth();
        
        System.out.println("CalendarPanel:" + firstYear + ":" + firstMonth);
        System.out.println("CalendarPanel:" + lastYear + ":" + lastMonth);
        
        for(int y = firstYear; y <= lastYear; ++y){
            CalendarRowPanel row = new CalendarRowPanel(table, y);
            
            vPanel.add(row);
            vPanel.add(createSeparator());
        }
        
        repaint();
    }
    
    private JSeparator createSeparator(){
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setMaximumSize(new Dimension(10000, 2));
        sep.setMinimumSize(new Dimension(1, 2));
        sep.setPreferredSize(new Dimension(10000, 2));
            
        return sep;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vPanel = new javax.swing.JPanel();
        downloadButton = new javax.swing.JButton();

        vPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        vPanel.setLayout(new javax.swing.BoxLayout(vPanel, javax.swing.BoxLayout.Y_AXIS));

        downloadButton.setText("Download");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .addComponent(downloadButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(downloadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downloadButton;
    private javax.swing.JPanel vPanel;
    // End of variables declaration//GEN-END:variables
}
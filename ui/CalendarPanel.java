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

import archive.CalendarTableModel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class CalendarPanel extends JPanel implements TableModelListener{
    private CalendarTableModel model;
    
    public CalendarPanel(CalendarTableModel model) {
        this.model = model;
        
        initComponents();
        
        monthTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        monthTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        monthTable.addMouseListener(new MouseEventHandler());
        
        monthTable.setModel(model);
        setColumnWidth(monthTable, model.getColumnWidth());
        
//        TableColumnModel columnModel = monthTable.getColumnModel();
//        TableColumn colorColumn = columnModel.getColumn(1);
//        colorColumn.setCellRenderer(new ColorCellRenderer());
        
        model.addTableModelListener(this);
    }
    
    private void setColumnWidth(JTable table, int[] width) {
        TableColumnModel columnModel = table.getColumnModel();
        
        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(width[i]);
        }
    }
    
    @Override
    public void tableChanged(TableModelEvent arg0) {
        this.repaint();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        monthTable = new javax.swing.JTable();

        monthTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(monthTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable monthTable;
    // End of variables declaration//GEN-END:variables
}
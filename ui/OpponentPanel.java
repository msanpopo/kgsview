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

import game.property.Player;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import app.Resource;

import statistics.SimpleTableModel;

@SuppressWarnings("serial")
public class OpponentPanel extends JPanel implements TableModelListener{
    private JTable table;
    private TableRowSorter<SimpleTableModel> sorter;
    
    public OpponentPanel(SimpleTableModel model) {
        JPanel innerPanel;
        
        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseEventHandler());
        
        sorter = new TableRowSorter<SimpleTableModel>(model);
        
        table.setModel(model);
        table.setRowSorter(sorter);
        
        setColumnWidth(table, model.getColumnWidth());
        
        model.addTableModelListener(this);
        
        innerPanel = new JPanel(new GridLayout(1, 1));
        innerPanel.setBorder(BorderFactory.createTitledBorder(Resource.get("opponent")));
        innerPanel.add(new JScrollPane(table));
        innerPanel.setPreferredSize(new Dimension(320, 140));
        
        this.setLayout(new BorderLayout());
        this.add(innerPanel, BorderLayout.CENTER);
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
    
    private class MouseEventHandler extends MouseAdapter {
        public MouseEventHandler() {
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            checkPopup(e);
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            checkPopup(e);
        }
        
        private void checkPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                JTable table = (JTable) e.getSource();
                
                int row = table.rowAtPoint(e.getPoint());

                Player player = (Player) table.getValueAt(row, 0);
                System.out.println("mouse pressed :" + player.getName());

                player.showPopup(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
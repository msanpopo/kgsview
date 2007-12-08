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

import app.Resource;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import statistics.SimpleTableModel;

public class CustomPanel extends JPanel implements TableModelListener{
    private JTable table;
    
    public CustomPanel(SimpleTableModel model) {
        JPanel innerPanel;
        
        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);
        
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
    public void tableChanged(TableModelEvent e) {
        this.repaint();
    }
}
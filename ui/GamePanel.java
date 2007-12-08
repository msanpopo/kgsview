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

import game.Game;
import game.GameTableModel;
import game.GoColor;
import game.UserResult;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

@SuppressWarnings("serial")
public class GamePanel extends JPanel  implements TableModelListener{
    private JTable table;
    private TableRowSorter<GameTableModel> sorter;
    private GameTableModel model;
    
    public GamePanel(GameTableModel model){
        this.model = model;
        
        table = new JTable();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseEventHandler());
        
        sorter = new TableRowSorter<GameTableModel>(model);
        
        table.setModel(model);
        table.setRowSorter(sorter);
        setColumnWidth(table, model.getColumnWidth());
        
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn colorColumn = columnModel.getColumn(1);
        colorColumn.setCellRenderer(new ColorCellRenderer());
        
        TableColumn resultColumn = columnModel.getColumn(6);
        resultColumn.setCellRenderer(new ResultCellRenderer());
        
        model.addTableModelListener(this);
        
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }
    
    @Override
    public void tableChanged(TableModelEvent arg0) {
        // sorter.toggleSortOrder(3);
        this.repaint();
    }
    
    class CenterCellRenderer extends DefaultTableCellRenderer{
        private DefaultTableCellRenderer renderer;
        
        public CenterCellRenderer(){
            setHorizontalAlignment(JLabel.CENTER);
        }
    }
    
    class ColorCellRenderer implements TableCellRenderer{
        private CenterCellRenderer bRenderer;
        private CenterCellRenderer wRenderer;
        
        public ColorCellRenderer(){
            bRenderer = new CenterCellRenderer();
            bRenderer.setBackground(Color.BLACK);
            bRenderer.setForeground(Color.WHITE);
            //bRenderer.setHorizontalAlignment(JLabel.CENTER);
            wRenderer = new CenterCellRenderer();
            wRenderer.setBackground(Color.WHITE);
            wRenderer.setForeground(Color.BLACK);
            //wRenderer.setHorizontalAlignment(JLabel.CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object val, boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel l = (JLabel)wRenderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
            
            if(val instanceof GoColor){
                GoColor c = (GoColor)val;
                
                if(c == GoColor.BLACK){
                    l = (JLabel)bRenderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
                    
                }else{
                    l = (JLabel)wRenderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
                }
            }
            
            return l;
        }
    }
    
    class ResultCellRenderer implements TableCellRenderer{
        private final Color winColor = new Color(0xcc, 0xff, 0xcc);
        private final Color drawColor = new Color(0xff, 0xff, 0xcc);
        private final Color loseColor = new Color(0xff, 0xcc, 0xcc);
        
        private DefaultTableCellRenderer winRenderer;
        private DefaultTableCellRenderer drawRenderer;
        private DefaultTableCellRenderer loseRenderer;
        private DefaultTableCellRenderer unknownRenderer;
        
        public ResultCellRenderer(){
            winRenderer = new CenterCellRenderer();
            winRenderer.setBackground(winColor);
            winRenderer.setForeground(Color.BLACK);
            
            drawRenderer = new CenterCellRenderer();
            drawRenderer.setBackground(drawColor);
            drawRenderer.setForeground(Color.BLACK);
            
            loseRenderer = new CenterCellRenderer();
            loseRenderer.setBackground(loseColor);
            loseRenderer.setForeground(Color.BLACK);
        
            unknownRenderer = new CenterCellRenderer();
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object val, boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel l = (JLabel)winRenderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
            
            if(val instanceof UserResult){
                UserResult result = (UserResult)val;
                switch(result){
                    case WIN:
                        l = (JLabel)winRenderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
                        break;
                    case JIGO:
                        l = (JLabel)drawRenderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
                        break;
                    case LOSE:
                        l = (JLabel)loseRenderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
                        break;
                    case UNKNOWN:
                        l = (JLabel)unknownRenderer.getTableCellRendererComponent(table, val, isSelected, hasFocus, row, col);
                        break;
                    default:
                        System.err.println("error:ResultCellRenderer:ありえん");
                        break;
                }
            }
            
            return l;
        }
    }
    
    private void setColumnWidth(JTable table, int[] width) {
        TableColumnModel columnModel = table.getColumnModel();
        
        for (int i = 0; i < columnModel.getColumnCount(); ++i) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(width[i]);
        }
    }
    
    private class MouseEventHandler extends MouseAdapter{
        public MouseEventHandler(){}
        
        @Override
        public void mousePressed(MouseEvent e) {
            checkPopup(e);
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            checkPopup(e);
        }
        
        private  void checkPopup(MouseEvent e){
            // System.out.println("mouse pressed :" + row + " e:" + e);
            
            if (e.isPopupTrigger()) {
                JTable table = (JTable) e.getSource();
                int row = table.rowAtPoint(e.getPoint());
                int modelRow = table.convertRowIndexToModel(row);
                Game game = model.getGame(modelRow);
                //System.out.println("GamePanel.checkPopup:game:" + game);
                game.showPopup(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
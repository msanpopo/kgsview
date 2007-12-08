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

package statistics;

import app.Resource;
import game.Game;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class SimpleTableModel extends AbstractTableModel{
    private static final String[] DEFAULT_HEADER = {
        "",
        Resource.get("games"),
        Resource.get("win"),
        Resource.get("lose"),
        Resource.get("draw"),
        Resource.get("winRatio")
    };
    private static final int[] DEFAULT_COLUMN_WIDTH = {80, 50, 40, 40, 40, 40, 50};
    
    private String[] header;
    private int[] columnWidth;
    private String tableName;
    protected ArrayList<RowModel> rowList;      // リストが固定で中の数値だけが変わるものと、リスト自身がゴッソリ変わる場合がある。
    
    public SimpleTableModel() {
        header = DEFAULT_HEADER;
        columnWidth = DEFAULT_COLUMN_WIDTH;
        tableName = "";
        rowList = new ArrayList<RowModel>();
    }
    
    public void clear(){
        for(RowModel row : rowList){
            row.clear();
        }
    }
    
    public void addGame(Game game){
        for(RowModel row : rowList){
            row.addGame(game);
        }
    }
    
    public void finish(){
        fireTableDataChanged();
    }
    
    public void setHeader(String[] header){
        this.header = header;
    }
    
    public void setColumnWidth(int[] columnWidth){
        this.columnWidth = columnWidth;
    }
    
    public void setTableName(String name){
        tableName = name;
    }
    
    public String getTableName(){
        return tableName;
    }
    
    public void addRow(RowModel row){
        rowList.add(row);
    }
    
    public int[] getColumnWidth(){
        return columnWidth;
    }
    
    @Override
    public int getRowCount() {
        return rowList.size();
    }
    
    @Override
    public int getColumnCount() {
        return header.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return header[col];
    }
    
    @Override
    public Class<?> getColumnClass(int c) {
        if(rowList.size() == 0){
            return String.class;         //  rowList 中身がない時にヘッダーを触ってソートすると落ちるので、その場しのぎに逃げる。
        }else{
            return getValueAt(0, c).getClass();
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int colIndex) {
        // System.out.println(" :" + tableName + " row:" + rowIndex + " col:" + colIndex);
        RowModel row = rowList.get(rowIndex);
        
        return row.get(colIndex);
    }
}
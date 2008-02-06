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

package archive;

import javax.swing.table.AbstractTableModel;

public class CalendarTableModel  extends AbstractTableModel{
    private static final String[] header = {"", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

    private static final int[] columnWidth = {70, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
    
    private Archive archive;
    
    public CalendarTableModel(){
        this.archive = null;
    }
    
    public void setArchive(Archive archive){
        this.archive = archive;
        
        fireTableDataChanged();   
    }

    public int[] getColumnWidth(){
        return columnWidth;
    }
    
    @Override
    public String getColumnName(int column){
        return header[column];
    }
    
    @Override
    public int getRowCount(){
        if(archive != null && archive.hasCalendar()){
            int first = archive.getFirstYear();
            int last = archive.getLastYear();
        
            return last - first + 1;      
        }else{
            return 0;
        }
    }
    
    @Override
    public int getColumnCount(){
        return header.length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        // System.out.println("CalendarTableModel.getColumnClass:" + columnIndex);
        switch(columnIndex){
            case 0:
                return Object.class;
            default:
                return Object.class;
        }
    }
    
    @Override
    public Object getValueAt(int row, int column){
        if(archive == null){
            return "";
        }
        
        int year = row + archive.getFirstYear();
        int month = column;
        
        boolean hasData = archive.hasCalendar(year, month);
        
        switch(column){
            case 0:
                return year;
            default:
                if(hasData){
                    return Boolean.valueOf(false);
                }else{
                    return "";
                }
        }
    }
}
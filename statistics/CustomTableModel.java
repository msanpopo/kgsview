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

import app.App;
import app.Resource;
import game.Game;
import java.util.ArrayList;
import statistics.filter.FilterList;
import statistics.filter.GameFilter;

public class CustomTableModel extends SimpleTableModel{
    public static final String[] HEADER = {
        Resource.get("condition"),
        Resource.get("games"),
        Resource.get("win"),
        Resource.get("lose"),
        Resource.get("draw"),
        Resource.get("winRatio")
    };
    public static final int[] COLUMN_WIDTH = { 350, 60, 60, 60, 60, 60, 50 };
    
    private ArrayList<ResultRow> resultList;
    
    public CustomTableModel() {
        super();
        
        setHeader(HEADER);
        setColumnWidth(COLUMN_WIDTH);
        
        setTableName("custom");
    }
    
    public void build(){
        //System.out.println("CustomTablemodel.build");
        
        FilterList list = App.getInstance().getFilterList();
        
        rowList = new ArrayList<RowModel>();
        
        for(GameFilter f : list.getCollection()){
            if(f.isMarked()){
                // System.out.println("CustomTablemodel.build:" + f.getName());
                ResultRow rr = new ResultRow(f);
                
                addRow(rr);
            }
        }
    }
    
    @Override
    public void addGame(Game game){
        for(RowModel rm : rowList){
            ResultRow r = (ResultRow)rm;
            r.addGame(game);
        }
    }
    
    @Override
    public void finish(){
        fireTableDataChanged();
    }
}
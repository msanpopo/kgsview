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
import game.property.Player;
import java.util.ArrayList;
import java.util.TreeMap;

public class OpponentTableModel extends SimpleTableModel{
    public static final String[] HEADER = {
        Resource.get("opponent"),
        Resource.get("games"),
        Resource.get("win"),
        Resource.get("lose"),
        Resource.get("draw"),
        Resource.get("winRatio")
    };
    public static final int[] COLUMN_WIDTH = { 100, 60, 60, 60, 60, 60, 50 };
    
    private TreeMap<String, ResultRow> opponent;
    
    public OpponentTableModel() {
        super();
        
        setHeader(HEADER);
        setColumnWidth(COLUMN_WIDTH);
        
        setTableName("opponent");
        
        opponent = new TreeMap<String, ResultRow>();
    }
    
    @Override
    public void clear(){
        rowList = new ArrayList<RowModel>();
        opponent = new TreeMap<String, ResultRow>();
    }
    
    @Override
    public void addGame(Game game){
        Player opponentPlayer = game.getOpponent();
        ResultRow rs = opponent.get(opponentPlayer.getName());      // 同じ対局者でも player は別のオブジェクトになってるので
        if(rs == null){
            ResultRow newResultSet = new ResultRow(opponentPlayer);
            newResultSet.addGame(game);
            opponent.put(opponentPlayer.getName(), newResultSet);
        }else{
            rs.addGame(game);
        }
    }
    
    @Override
    public void finish(){
        for(String name : opponent.keySet()){
            ResultRow rs = opponent.get(name);
            addRow(rs);
        }
        
        fireTableDataChanged();
    }
}
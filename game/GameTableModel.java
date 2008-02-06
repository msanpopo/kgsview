/*
 * KGSview - KGS(ネット碁会所)用戦績表示アプリケーション
 *
 * Copyright (C) 2006, 2007, 2008 sanpo
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

package game;

import game.property.Rank;

import javax.swing.table.AbstractTableModel;

import app.Resource;
import game.property.StartTime;

@SuppressWarnings("serial")
public class GameTableModel  extends AbstractTableModel{
    private static final String[] header = {
        Resource.get("type"),
        Resource.get("color"),
        Resource.get("rank"),
        Resource.get("opponent"),
        Resource.get("setup"),
        Resource.get("date"),
        Resource.get("result"),
        Resource.get("result"),
        Resource.get("sgf"),
    };
    private static final int[] columnWidth = {50, 20, 40, 260, 70, 110, 40, 50, 50, 50, 40};
    
    private Game[] gameArray;       //日付の新しい順
    private String userName;
    
    public GameTableModel(){
        gameArray = new Game[0];
        userName = "";
    }
    
    public void setGameArray(Game[] array, String name){
        this.gameArray = array;
        this.userName = name;
        
        fireTableDataChanged();   
    }
    
    public String getUserName(){
        return userName;
    }
        
    public Game getGame(int index){
        if(gameArray.length == 0 || index < 0 || index > gameArray.length - 1){
            return null;
        }else{
            return gameArray[index];
        }
    }
    
    public Game last(){
        if(gameArray.length != 0){
            return gameArray[0];
        }else{
            return null;
        }
    }
    
    public Game first(){
        int l = gameArray.length;
        if(l != 0){
            return gameArray[l - 1];
        }else{
            return null;
        }
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
        return gameArray.length;
    }
    
    @Override
    public int getColumnCount(){
        return header.length;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex){
        // System.out.println("GameTableModel.getColumnClass:" + columnIndex);
        switch(columnIndex){
            case 1:
                return GoColor.class;
            case 2:
                return Rank.class;
            case 5:
                return StartTime.class;
            default:
                return Object.class;
        }
    }
    
    @Override
    public Object getValueAt(int row, int column){
        Game game = gameArray[row];
        
        switch(column){
            case 0:
                return game.getGameType();
            case 1:
                return game.getUserColor();
            case 2:
                return game.getUserRank();
            case 3:
                return game.getPlayerString(userName);
            case 4:
                return game.gameSetup;
            case 5:
                return game.startTime;
            case 6:
                return game.getUserResult();
            case 7:
                return game.result;
            case 8:
                return game.sgfFile;
            default:
                return "unknown";
        }
    }
}
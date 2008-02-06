/*
 * KGSview - KGS(ネット碁会所)用戦績表示アプリケーション
 *
 * Copyright (C) 2006, 2007 sanpo
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

import game.Game;
import game.property.Rank;

public class MonthRow implements RowModel{
    private static final int SIZE = 8;
                    
    private int year;
    private int month;
    private ResultRow resultRow;
    private Rank rankMax;
    private Rank rankMin;
    
    public MonthRow(int year, int month){
        this.year = year;
        this.month = month;
        this.resultRow = new ResultRow();
        this.rankMax = Rank.MinRank();
        this.rankMin = Rank.MaxRank();
    }
    
    public int getYear(){
        return year;
    }
    
    public int getMonth(){
        return month;
    }
    
    @Override
    public void addGame(Game game) {
        Rank rank = game.getUserRank();
        resultRow.addGame(game);
        
        if(rankMax.compareTo(rank) < 0){
            rankMax = rank;
        }
        if(rankMin.compareTo(rank) > 0){
            rankMin = rank;
        }
    }
    
    @Override
    public void clear() {
        resultRow.clear();
        rankMax = Rank.MinRank();
        rankMin = Rank.MaxRank();
    }
    
    @Override
    public Object get(int index) {
        switch(index){
            case 0:
                return Integer.toString(year) + "/" + Integer.toString(month);
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return resultRow.get(index);
            case 6:
                return rankMax.toString();
            case 7:
                return rankMin.toString();
            default:
                return "???";
        }
    }
    
    @Override
    public int getSize() {
        return SIZE;
    }
}
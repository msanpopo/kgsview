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

import game.Game;
import statistics.filter.GameFilter;

public class ResultRow implements RowModel{
    private static final int SIZE = 6;
    
    private Object label;
    private int n;	// 総数 ( = winN + loseN + drawN + その他 )
    private int winN;	// 勝ち数
    private int loseN;	// 敗け数
    private int jigoN;	// 持碁数
    
    private GameFilter filter;
    
    public ResultRow(){
        this.label = "";
        
        this.filter = null;
    }
    
    public ResultRow(Object  label){
        this.label = label;
        
        this.filter = null;
    }
    
    public ResultRow(GameFilter filter){
        this.label = filter.getName();
        
        this.filter = filter;
    }
    
    public void add(ResultRow rs){	// 集計用
        n += rs.n;
        winN += rs.winN;
        loseN += rs.loseN;
        jigoN += rs.jigoN;
    }
    
    @Override
    public void addGame(Game game){
        if(filter != null && filter.checkGame(game) == false){
               return;
        }
        
        this.n += 1;
        
        switch(game.getUserResult()){
            case WIN:
                winN += 1;
                break;
            case LOSE:
                loseN += 1;
                break;
            case JIGO:
                jigoN += 1;
                break;
            default:
                break;
        }
    }
    
    @Override
    public void clear(){
        n = 0;
        winN = 0;
        loseN = 0;
        jigoN = 0;
    }
    
    public Object getLabel(){
        return label;
    }
    
    public int getN(){
        return n;
    }
    
    public int getWinN(){
        return winN;
    }
    
    public int getLoseN(){
        return loseN;
    }
    
    public int getJigoN(){
        return jigoN;
    }
    
    public int getWinRate(){
        if(winN + loseN + jigoN == 0){
            return 0;
        }else{
            return (int)(100 * (double)winN / (winN + loseN + jigoN));
        }
    }
    
    @Override
    public Object get(int index) {
        switch(index){
            case 0:
                return label;
            case 1:
                return n;
            case 2:
                return winN;
            case 3:
                return loseN;
            case 4:
                return jigoN;
            case 5:
                return getWinRate();
            default:
                return "???";
        }
    }
    
    @Override
    public int getSize() {
        return SIZE;
    }
    
    public void debugPrint(){
        System.out.print("set: n:" + n);
        System.out.print("  winN:" + winN);
        System.out.print("  loseN:" + loseN);
        System.out.println("  jigoN:" + jigoN);
    }
}
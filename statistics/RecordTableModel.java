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
import game.UserResult;

public class RecordTableModel extends SimpleTableModel{
    public static final String[] HEADER = {
        "", 
        Resource.get("numberOfTimes"),
        Resource.get("start"),
        Resource.get("end")
    };
    public static final int[] COLUMN_WIDTH = {60, 50, 110, 110};
    
    private boolean stateWin;	// 連勝中なら ture
    private ConsecutiveRecord win;		// 連勝記録(ranked のみ)
    private ConsecutiveRecord lose;		// 連敗記録(ranked のみ)
    private ConsecutiveRecord winTmp;
    private ConsecutiveRecord loseTmp;
    
    public RecordTableModel() {
        setHeader(HEADER);
        setColumnWidth(COLUMN_WIDTH);
        
        setTableName("record");
        
        stateWin = true;	// 連勝中なら ture
        win = new ConsecutiveRecord(Resource.get("successiveWin"));
        lose = new ConsecutiveRecord(Resource.get("successiveLose"));
        winTmp = new ConsecutiveRecord("");
        loseTmp = new ConsecutiveRecord("");
        
        addRow(win);
        addRow(lose);
    }
    
    @Override
    public void addGame(Game game){
        UserResult result = game.getUserResult();
        
        if(result == UserResult.WIN){
            if(stateWin == false){
                loseTmp.clear();
                
                stateWin = true;
            }
            
            winTmp.addGame(game);
            if(win.getN() <= winTmp.getN()){
                win.copy(winTmp);
            }
        }else if(result == UserResult.LOSE){
            if(stateWin == true){
                winTmp.clear();
                
                stateWin = false;
            }
            
            loseTmp.addGame(game);
            if(lose.getN() <= loseTmp.getN()){
                lose.copy(loseTmp);
            }
        }
    }
}
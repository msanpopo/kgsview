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
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MonthTableModel extends SimpleTableModel{
    public static final String[] HEADER = {
        Resource.get("yearMonth"),
        Resource.get("games"),
        Resource.get("win"),
        Resource.get("lose"),
        Resource.get("draw"),
        Resource.get("winRatio"),
        Resource.get("maxRank"),
        Resource.get("minRank")
    };
    public static final int[] COLUMN_WIDTH = {60, 50, 40, 40, 40, 40, 60, 60};
    
    public MonthTableModel() {
        setHeader(HEADER);
        setColumnWidth(COLUMN_WIDTH);
        
        setTableName("month");
    }
    
    @Override
    public void clear(){
        rowList = new ArrayList<RowModel>();
    }
    
    @Override
    public void addGame(Game game){
        GregorianCalendar cal = new GregorianCalendar(App.getInstance().getTimeZone());
        int year;
        int month;
        MonthRow m;
        
        cal.setTime(game.getDate());
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1; /* １月が０ */
        
        m = searchMonthRow(year, month);
        if(m == null){
            m = new MonthRow(year, month);
            addRow(m);
        }
        
        m.addGame(game);
    }
    
    private MonthRow searchMonthRow(int year, int month){
        for(RowModel lm : rowList){
            MonthRow m = (MonthRow)lm;
            if(m.getYear() == year && m.getMonth() == month){
                return m;
            }
        }
        return null;
    }
}
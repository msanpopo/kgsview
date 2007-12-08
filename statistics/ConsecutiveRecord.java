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
import game.Game;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsecutiveRecord implements RowModel{
    private static final int SIZE = 4;
    
    private String label;
    private int n;
    private Date start;
    private Date end;
    
    public ConsecutiveRecord(String label){
        this.label = label;
        this.n = 0;
        this.start = null;
        this.end = null;
    }
    
    public int getN(){
        return this.n;
    }
    
    public void copy(ConsecutiveRecord cr){
        n = cr.n;
        start = cr.start;
        end = cr.end;
    }
    
    @Override
    public void addGame(Game game) {
        Date date = game.getDate();
        n += 1;
        if(start == null){
            start = date;
        }
        end = date;
    }
    
    @Override
    public void clear() {
        n = 0;
        start = null;
        end = null;
    }
    
    @Override
    public Object get(int index) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
        sdf.setTimeZone(App.getInstance().getTimeZone());
        
        switch(index){
            case 0:
                return label;
            case 1:
                return n;
            case 2:
                if(start == null){
                    return "";
                }else{
                    return sdf.format(start);
                }
            case 3:
                if(start == null){
                    return "";
                }else{
                    return sdf.format(end);
                }
            default:
                return "???";
        }
    }
    
    @Override
    public int getSize() {
        return SIZE;
    }
    
    public void debugPrint(){
        SimpleDateFormat sdf = new SimpleDateFormat("yy/M/dd HH:mm");
        
        System.out.println("n      :" + n);
        System.out.println("start  :" + sdf.format(start));
        System.out.println("end    :" + sdf.format(end));
    }
}
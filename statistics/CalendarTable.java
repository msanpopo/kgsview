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

package statistics;

import archive.Archive;
import java.util.ArrayList;

public class CalendarTable {
    private Archive archive = null;
    
    private ArrayList<CalendarTableListener> listener;
    
    public CalendarTable(){
        listener = new ArrayList<CalendarTableListener>();
    }
    
    public void addCalendarTableListener(CalendarTableListener newListener){
        if(listener.contains(newListener) == false){
            listener.add(newListener);
        }
    }
    
    public void removeCalendarTableListener(CalendarTableListener newListener){
        listener.remove(newListener);
    }
    
    public void setArchive(Archive archive){
        this.archive = archive;
        
        for(CalendarTableListener l : listener){
            l.calendarTableChanged(this);
        }
    }
    
    public boolean hasCalendar(){
        if(archive != null && archive.hasCalendar()){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean hasCalendar(int year, int month){
        if(archive != null && archive.hasCalendar(year, month)){
            return true;
        }else{
            return false;
        }
    }
    
    public void setDownloadMark(int year, int month, boolean mark){
        if(archive != null && archive.hasCalendar(year, month)){
            archive.setDownloadMark(year, month, mark);
        }
    }
    
    public int getFirstYear(){
        if(archive != null && archive.hasCalendar()){
            return archive.getFirstYear();
        }else{
            return 0;
        }
    }
    
    public int getFirstMonth(){
        if(archive != null && archive.hasCalendar()){
            return archive.getFirstMonth();
        }else{
            return 0;
        }
    }
        
    public int getLastYear(){
        if(archive != null && archive.hasCalendar()){
            return archive.getLastYear();
        }else{
            return 0;
        }
    }
    
    public int getLastMonth(){
        if(archive != null && archive.hasCalendar()){
            return archive.getLastMonth();
        }else{
            return 0;
        }
    }
}
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

package game.property;

import app.App;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

public class StartTime implements Comparable<StartTime>{    
    private String origStr;
    private Date startTime; // GMT
       
    // str の例:[1/1/06 1:01 PM]	午前０時は AM12 で、午後０時は PM12
    public StartTime(String str){
        origStr = str;

        // str は GMT
        //System.out.println("StartTime:in:" + str);
        
        SimpleDateFormat df = new SimpleDateFormat("M/d/yy h:m a", Locale.US); 
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        startTime = new Date();

        try {
            startTime = df.parse(str);
        } catch (ParseException ex) {
            System.out.println("error:StartTime:" + str + " : " + ex);
            ex.printStackTrace();
        }

        //System.out.println("###:" + startTime);
        /*
        GregorianCalendar calJst = new GregorianCalendar(TimeZone.getTimeZone("JST"));
        GregorianCalendar calGmt = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        calJst.setTime(startTime);
        calGmt.setTime(startTime);
        System.out.println("calJst : " + calJst);
        System.out.println("calGmt : " + calGmt);
        */
    }
    
    public String getOrigString(){
        return origStr;
    }

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
        sdf.setTimeZone(App.getInstance().getTimeZone());
        String str = sdf.format(startTime);

        return str;	// この文字列はローカルの時間帯になる
    }
    
    public Date getDate(){
        return startTime;
    }
    
    public void debugPrint(){
        System.out.println("StartTime:" + startTime.getTime());
    }
    
    @Override
    public int compareTo(StartTime st){
        return this.startTime.compareTo(st.startTime);
    }
}
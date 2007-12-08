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

package statistics.filter;

import app.App;
import game.Game;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

public class DateFilter extends GameFilter{
    public static final int TYPE_SPAN = 0;
    public static final int TYPE_BEFORE = 1;
    public static final int TYPE_AFTER = 2;
    public static final int TYPE_LAST = 3;
    
    private int type;
    
    private Date first;
    private Date last;
    private Date before;
    private Date after;
    private int days;
    
    public DateFilter() {
        super();
        
        this.type = TYPE_SPAN;
        
        this.first = new Date();
        this.last = new Date();
        this.before = new Date();
        this.after = new Date();
        this.days = 0;
    }
    
    @Override
    public GameFilter copy(){
        DateFilter filter = new DateFilter();
        filter.name = name + "(copy)";
        filter.marked = marked;
        filter.type = type;
        filter.first = first;
        filter.last = last;
        filter.before = before;
        filter.after = after;
        filter.days = days;
        
        return filter;
    }
    
    public int getType(){
        return type;
    }
    
    public Date getFirst(){
        return first;
    }
    
    public Date getLast(){
        return last;
    }
    
    public Date getBefore(){
        return before;
    }
    
    public Date getAfter(){
        return after;
    }
    
    public int getDays(){
        return days;
    }
    
    public void setSpan(Date first, Date last){
        this.type = TYPE_SPAN;
        
        this.first = first;
        this.last = last;
        this.before = new Date();
        this.after = new Date();
        this.days = 0;
    }
    
    public void setBefore(Date before){
        this.type = TYPE_BEFORE;
        
        this.first = new Date();
        this.last = new Date();
        this.before = before;
        this.after = new Date();
        this.days = 0;
    }
    
    public void setAfter(Date after){
        this.type = TYPE_AFTER;
        
        this.first = new Date();
        this.last = new Date();
        this.before = new Date();
        this.after = after;
        this.days = 0;
    }
    
    public void setLast(int days){
        this.type = TYPE_LAST;
        
        this.first = new Date();
        this.last = new Date();
        this.before = new Date();
        
        this.days = days;
        
        Date tmp = new Date();
        // System.out.println("DateFilter.setLast:days:" + days + ":tmp:" + tmp);
        long millisec = days * 24L * 60L * 60L * 1000L;
        this.after.setTime(tmp.getTime() - millisec);
        // System.out.println("DateFilter.setLast:days:" + days + ":aft:" + after);
    }
    
    @Override
    public boolean checkGame(Game game) {
        Date date = game.getDate();
        
        boolean retval = false;
        
        switch(type){
            case TYPE_SPAN:
                if(date.after(first) && date.before(last)){
                    retval = true;
                }else{
                    retval = false;
                }
                break;
            case TYPE_BEFORE:
                retval = date.before(before);
                break;
            case TYPE_AFTER:
                retval = date.after(after);
                break;
            case TYPE_LAST:
                //System.out.println("DateFilter: LAST0:" + date );
                //System.out.println("DateFilter: LAST1:" + after );
                retval = date.after(after);
                break;
            default:
                break;
        }
        
        return retval;
    }
    
    @Override
    public void readXmlLocal(Element e) {
        String nodeName = e.getNodeName();
        
        String str = getString(e);
        
        // System.out.println("DateFilter.readXml:" + nodeName + ":" + str);
        
        if(nodeName.equals("type")){
            type = Integer.parseInt(str);
            
        }else if(nodeName.equals("first")){
            first = stringToDate(str);
            
        }else if(nodeName.equals("last")){
            last = stringToDate(str);
            
        }else if(nodeName.equals("before")){
            before = stringToDate(str);
            
        }else if(nodeName.equals("after")){
            after = stringToDate(str);
            
        }else if(nodeName.equals("days")){
            days = Integer.parseInt(str);
        }
    }
    
    @Override
    public void readXmlFinished(){
        switch(type){
            case TYPE_SPAN:
                setSpan(first, last);
                break;
            case TYPE_BEFORE:
                setBefore(before);
                break;
            case TYPE_AFTER:
                setAfter(after);
                break;
            case TYPE_LAST:
                setLast(days);
                break;
            default:
                break;
        }
    }
    
    @Override
    public void writeXmlLocal(Document doc, Element e) {
        Element eType = doc.createElement("type");
        Text textType = doc.createTextNode(Integer.toString(type));
        eType.appendChild(textType);
        
        e.appendChild(eType);
        
        switch(type){
            case TYPE_SPAN:
                Element eFirst = doc.createElement("first");
                Text textFirst = doc.createTextNode(dateToString(first));
                eFirst.appendChild(textFirst);
                
                Element eLast = doc.createElement("last");
                Text textLast = doc.createTextNode(dateToString(last));
                eLast.appendChild(textLast);
                
                e.appendChild(eFirst);
                e.appendChild(eLast);
                break;
            case TYPE_BEFORE:
                Element eBefore = doc.createElement("before");
                Text textBefore = doc.createTextNode(dateToString(before));
                eBefore.appendChild(textBefore);
                
                e.appendChild(eBefore);
                break;
            case TYPE_AFTER:
                Element eAfter = doc.createElement("after");
                Text textAfter = doc.createTextNode(dateToString(after));
                eAfter.appendChild(textAfter);
                
                e.appendChild(eAfter);
                break;
            case TYPE_LAST:
                Element eDays = doc.createElement("days");
                Text textDays = doc.createTextNode(Integer.toString(days));
                eDays.appendChild(textDays);
                
                e.appendChild(eDays);
                break;
            default:
                break;
        }
    }
    
    private String dateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
        sdf.setTimeZone(App.getInstance().getTimeZone());
        String str = sdf.format(date);
        
        return str;	// この文字列はローカルの時間帯になる
    }
    
    private Date stringToDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
        sdf.setTimeZone(App.getInstance().getTimeZone());
        
        Date date = new Date();
        
        try {
            date = sdf.parse(str);
        } catch (ParseException ex) {
            System.out.println("error:DateFilter.stringToDate:" + str + " : " + ex);
            ex.printStackTrace();
        }
        
        return date;
    }
    /*
    private Date getZeroOClock(){
        return clearHourMinSec(new Date());
    }
    
    private Date clearHourMinSec(Date src){
        GregorianCalendar cal = new GregorianCalendar(App.getInstance().getTimeZone());
        
        cal.setTime(src);
        
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        Date dst = cal.getTime();
        System.out.println("src :" + src);
        System.out.println("dst :" + dst);
        return dst;
    }
    */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("DateFilter").append("\n");
        str.append("name    :" + name).append("\n");
        str.append("marked  :" + marked).append("\n");
        str.append("type    :" + type).append("\n");
        str.append("first   :" + first).append("\n");
        str.append("last    :" + last).append("\n");
        str.append("before:" + before).append("\n");
        str.append("after   :" + after).append("\n");
        str.append("days    :" + days).append("\n");
        return str.toString();
    }
}
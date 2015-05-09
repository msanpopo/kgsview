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

package archive;

import app.App;
import app.Resource;
import game.Game;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.TimeZone;
import java.util.TreeSet;

public class Archive {
    private static final String MONTH_LIST = "month.txt";
    
    private String name = "";
    private TreeSet<MonthGame> monthList = new TreeSet<MonthGame>();
    private TreeSet<Game> allGame = new TreeSet<Game>();
    
    public Archive(){
    }
        
    public Archive(String name){
        this.name = name;
    }

    public DownloadState getDownloadState(int year, int month) {
        for(MonthGame mg : monthList){
            if(mg.getYear() == year && mg.getMonth() == month){
                return mg.getDownloadState();
            }
        }
        
        return DownloadState.NOT_DOWNLOADED;
    }
    
    public String getName(){
        return name;
    }
    
    public int getSize(){
        return allGame.size();
    }
    
    public Collection<Game> getCollection(){
        return allGame;
    }
    
    public boolean hasCalendar(){
        if(monthList.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    
    public boolean hasCalendar(int year, int month){
        if(monthList.isEmpty()){
            return false;
        }
        
        int firstYear = monthList.first().getYear();
        int firstMonth = monthList.first().getMonth();
        int lastYear = monthList.last().getYear();
        int lastMonth = monthList.last().getMonth();
        
        boolean retval = false;
        
        if(year >= firstYear && year <= lastYear){
            retval = true;
            
            if(year == firstYear && month < firstMonth){
                retval = false;
            }else if(year == lastYear && month > lastMonth){
                retval = false;
            }
        }
        
//        System.out.println("Archive.hasCalendar:" + year + "/" + month + " : " + retval);
        return retval;
    }
    
    public void setDownloadMark(int year, int month, boolean mark){
        for(MonthGame mg : monthList){
            if(mg.getYear() == year && mg.getMonth() == month){
                mg.setDownloadMark(mark);
            }
        }
    }
    
    public boolean getDownloadMark(int year, int month){
        for(MonthGame mg : monthList){
            if(mg.getYear() == year && mg.getMonth() == month){
                return mg.getDownloadMark();
            }
        }
        return false;
    }
    
    public int getFirstYear(){
        if(hasCalendar()){
            return monthList.first().getYear();
        }else{
            return 0;
        }
    }
    
    public int getFirstMonth(){
        if(hasCalendar()){
            return monthList.first().getMonth();
        }else{
            return 0;
        }
    }
    
    public int getLastYear(){
        if(hasCalendar()){
            return monthList.last().getYear();
        }else{
            return 0;
        }
    }
    
    public int getLastMonth(){
        if(hasCalendar()){
            return monthList.last().getMonth();
        }else{
            return 0;
        }
    }
    
    public void write(){
        if(name == null || name.isEmpty() || monthList.isEmpty()){
            return;
        }
        
        File appDir = App.getInstance().getAppDir();
        File topDir = new File(appDir, name);
        File listFile = new File(topDir, MONTH_LIST);
        
        if(topDir.exists() == false){
            topDir.mkdir();
        }

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(listFile)));
            
            for (MonthGame mg : monthList) {
                mg.writeState(pw);
                mg.write(topDir);
            }
        } catch (IOException ex) {
            System.err.println("error:Archive write:" + ex);
        } finally {
            if (pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }
    
    public void read(){
        if(name == null || name.isEmpty()){
            return;
        }
        
        File appDir = App.getInstance().getAppDir();
        File topDir = new File(appDir, name);
        File listFile = new File(topDir, MONTH_LIST);
        
        if(listFile.exists() == false){
            return;
        }
        
        BufferedReader br = null;
        try{
            String line;
            
            br = new BufferedReader(new FileReader(listFile));
            
            while((line = br.readLine()) != null){
                MonthGame monthGame = new MonthGame(name, line);
                monthGame.read(topDir);
                
                monthList.add(monthGame);
            }
            
        }catch(IOException ex){
            System.out.println("Archive read :: err:" + name + " :: " + ex);
            
        }finally{
            if(br != null){
                try{
                    br.close();
                }catch(IOException ex){
                    System.err.println("error:Archive:br.close:" + ex);
                }
            }
        }
        
        allListUpdate();
    }
    
    /**
     * 月リストの更新と現在の月の対局リストの再取得を行う。（年と月を指定せずに表示する web ページ）
     */
    public void calendarUpdate(){
        // TODO タイムゾーン決め打ち
        Page page = new Page(name, TimeZone.getDefault());
        page.download(null);
        
        if(page.hasMonthList()){
            for(MonthGame mg : page.getMonthList()){
                if(getMonthGame(mg.getYear(), mg.getMonth()) == null){
                    mg.setDownloadMark(true);
                    monthList.add(mg);
                }
            }
            
            if(page.hasGameTable()){
                MonthGame newMg = page.getMonthGame();
                MonthGame oldMg = getMonthGame(newMg.getYear(), newMg.getMonth());
                monthList.remove(oldMg);
                monthList.add(newMg);
            }
            
            allListUpdate();
            write();
        }
    }
    
    public void download(Downloader downloader){
        int nDownload = 0;
        int nCurrent = 0;
        for(MonthGame mg : monthList){
            if(mg.getDownloadMark()){
                nDownload += 1;
            }
        }
        
        for(MonthGame mg : monthList){
            System.out.println("Archive.download:" + mg.getYear() + "/" + mg.getMonth());
            
            if(mg.getDownloadMark() == false){
                continue;
            }
            
            nCurrent += 1;
            
            String msg = Resource.get("downloading") + " " + nCurrent + " / " + nDownload;
            downloader.setDownloadStatus(msg);
            
            System.out.println("Archive: before download");
            
            boolean success = mg.download(downloader);
            
            System.out.println("Archive.download: success:" + success);
            
            if(success && mg != monthList.last()){
                mg.setCompleted();
            }
            
            if(downloader.isCanceled()){
                System.out.println("Archive.download : cancel");
                return;
            }
            
            try {
                // 2ch で kgs の管理者がこのようなツールを作る時はダウンロード間隔３秒以上あけるように言ってたという
                // 書き込みを見たのでそれにならう。
                // matlab に６秒という記述を見つけたのでのばした(2015/5/9)
             

                Thread.sleep(6000);
            } catch (InterruptedException ex) {
                System.err.println("error:Archive:sleep():" + ex);
                return;
            }
        }
        
        allListUpdate();
        write();
    }
    
    private MonthGame getMonthGame(int year, int month){
        for(MonthGame mg : monthList){
            if(mg.getYear() == year && mg.getMonth() == month){
                return mg;
            }
        }
        
        return null;
    }
    
    private void allListUpdate(){
        allGame.clear();
        
        for(MonthGame mg : monthList){
            if(mg.hasGameList()){
                for(Game g : mg.getCollection()){
                    allGame.add(g);
                }
            }
        }
    }
}
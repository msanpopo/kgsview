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

import game.Game;
import game.RengoGame;
import game.RengoReviewGame;
import game.ReviewGame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ある月の対局一覧
 * 
 * <pre>
 * 
 * </pre>
 */
public class MonthGame implements Comparable<MonthGame>{
    private String name;
    private int year;
    private int month;

    private boolean completed;
    private boolean downloadMark;
        
    private ArrayList<Game> gameList = null;
    
    public MonthGame(String name, int year, int month){
        this.name = name;
        this.year = year;
        this.month = month;
        this.completed = false;
        this.downloadMark = false;
    }
    
    public MonthGame(String name, String line){
        this.name = name;
        
        readState(line);
        
        this.downloadMark = false;
    }
    
    public int getYear(){
        return year;
    }
    
    public int getMonth(){
        return month;
    }
    
    public void setGameList(ArrayList<Game> newList){
        gameList = newList;
        for(Game g : gameList){
            g.checkUser(name);
        }
    }
    
    public void setDownloadMark(boolean mark){
        System.out.println("MonthGame :" + year + "/" + month + " " + mark);
        downloadMark = mark;
    }
    
    public boolean getDownloadMark(){
        return downloadMark;
    }
    
    public DownloadState getDownloadState(){
        if(hasGameList()){
            if(completed){
                return DownloadState.DOWNLOADED_COMPLETELY;
            }else{
                return DownloadState.DOWNLOADED_INCOMPLETELY;
            }
        }else{
            return DownloadState.NOT_DOWNLOADED;
        }
    }
    
    public void setCompleted(){
        completed = true;
    }
    
    public Collection<Game> getCollection(){
        return gameList;
    }
    
    public boolean hasGameList(){
        if(gameList == null){
            return false;
        }else{
            return true;
        }
    }
    
    /* 例："2008-01,false" */
    public void writeState(PrintWriter pw){
        pw.print(year);
        pw.print("-");
        if(month < 10){
            pw.print("0");
        }
        pw.print(month);
        pw.print(",");
        pw.println(completed);
    }
    
    /* line:"2008-01,false" */
    public void readState(String line){
        String[] str0 = line.split(",");
        String[] str1 = str0[0].split("-");
        
        this.year = Integer.parseInt(str1[0]);
        this.month = Integer.parseInt(str1[1]);
        this.completed = Boolean.parseBoolean(str0[1]);
    }
    
    public void write(File topDir){
        if(name == null || name.isEmpty() || gameList == null || gameList.isEmpty()){
            return;
        }
        
        File csvFile = getCsvFile(topDir);
        
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new BufferedWriter(new FileWriter(csvFile)));
            for(Game game : gameList){
                game.write(pw);
            }
        }catch(IOException ex){
            System.out.println("MonthGame write :: error:" + ex);
        }finally{
            if(pw != null){
                pw.flush();
                pw.close();
            }
        }
    }
    
    public void read(File topDir){
        if(name == null || name.isEmpty()){
            return;
        }
        
        gameList = null;
        
        File csvFile = getCsvFile(topDir);
        
        if(csvFile.exists() == false){
            return;
        }
        
        BufferedReader br = null;
        try{
            String line;
            Pattern p = Pattern.compile("\"(.*)\""); // '"' に囲まれた文字列を抜き取る
            
            br = new BufferedReader(new FileReader(csvFile));
            
            while((line = br.readLine()) != null){
                ArrayList<String> l = new ArrayList<String>();
                String[] strArray = line.split(",");

                for(String s : strArray){
                    Matcher m = p.matcher(s);
                    
                    m.matches();
                    l.add(m.group(1));
                }
                
                Game game;
                if(l.get(0).equals("Rengo")){
                    game = new RengoGame(l);
                }else if(l.get(0).equals("Review") || l.get(0).equals("Demonstration")){
                    game = new ReviewGame(l);
                }else if(l.get(0).equals("Rengo Review")){
                    game = new RengoReviewGame(l);
                }else {
                    game = new Game(l);
                }
                game.checkUser(name);
                        
                if(gameList == null){
                    gameList = new ArrayList<Game>();
                }
                gameList.add(game);
            }
           
        }catch(IOException ex){
            System.out.println("MonthGame read :: err:" + name + " :: " + ex);
            gameList = null;
        }finally{
            if(br != null){
                try{
                    br.close();
                }catch(IOException ex){
                    System.err.println("error:MonthGame:br.close:" + ex);
                }
            }
        }
    }
    
    public boolean download(GameListDownloader downloader){
        if(downloadMark == false){
            return false;
        }
        
        downloadMark = false;
        
        PageLoader loader = new PageLoader(name, false, TimeZone.getDefault(), year, month);
        Page page = loader.download(downloader);
        
        if(page == null){
            return false;
        }else{
            gameList = page.getMonthGame().gameList;
            
            return true;
        }
    }
    
    private File getCsvFile(File topDir){       
        StringBuilder str = new StringBuilder();
        str.append(year);
        if(month < 10){
            str.append("0").append(month);
        }else{
            str.append(month);
        }
        str.append(".csv");
        
        File csvFile = new File(topDir, str.toString());
        
        return csvFile;
    }
            
    @Override
    public int compareTo(MonthGame mg){
        int mgNum = mg.year * 100 + mg.month;
        int num = year * 100 + month;
        
        if(mgNum < num){
            return 1;
        }else if(mgNum == num){
            return 0;
        }else{
            return -1;
        }
    }

}
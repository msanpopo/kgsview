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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ある月の対局一覧
 * 
 * <pre>
 * 
 * </pre>
 */
class MonthGame {
    private String name;
    private int year;
    private int month;
    
    private ArrayList<Game> gameList = null;
    
    public MonthGame(String name, int year, int month){
        this.name = name;
        this.year = year;
        this.month = month;
    }
    
    public int getYear(){
        return year;
    }
    
    public int getMonth(){
        return month;
    }
    
    public void setGameList(ArrayList<Game> newList){
        gameList = newList;
    }
    
    public void write(File topDir){
        if(name == null || name.isEmpty() || gameList.isEmpty()){
            return;
        }
        
        File csvFile = getCsvFile(topDir);
        
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(new FileWriter(csvFile));
            for(Game game : gameList){
                game.write(bw);
                bw.newLine();
            }
        }catch(IOException ex){
            System.out.println("MonthGame write :: error:" + ex);
        }finally{
            if(bw != null){
                try{
                    bw.flush();
                    bw.close();
                }catch(IOException e){
                    System.out.println("MonthGame flush  close :: error:" + e);
                }
            }
        }
    }
    
    public void read(File topDir){
        if(name == null || name.isEmpty()){
            return;
        }
        
        if(gameList == null || gameList.isEmpty() == false){
            gameList = new ArrayList<Game>();
        }
        
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
                gameList.add(game);
            }
           
        }catch(IOException ex){
            System.out.println("MonthGame read :: err:" + name + " :: " + ex);
            
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
    
    public void update(){
        
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
}
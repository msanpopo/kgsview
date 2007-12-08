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

package game;

import app.App;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import action.util.GameListDownloader;

public class GameList {
    private String userName;
    TreeSet<Game> gameSet;	// 日付順にならべる
    
    public GameList(){
        userName = "";
        gameSet = new TreeSet<Game>();
    }
    
    public GameList(String name){
        if(name == null){
            userName = "";
        }else{
            userName = name;
        }
        gameSet = new TreeSet<Game>();
    }
    
    public int getSize(){
        return gameSet.size();
    }
    
    public Collection<Game> getGameCollection(){
        return gameSet;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void addGameList(ArrayList<Game> newList){
        for(Game g : newList){
            g.checkUser(userName);
            addGame(gameSet, g);
        }
    }
    
    private static void addGame(TreeSet<Game> gameSet, Game game){
        if(gameSet.add(game) == false){
            // System.out.println("GameList:addGame: duplicated ::" + game);
        }
    }
    
    public void write(){
        File appDir = App.getInstance().getAppDir();
        BufferedWriter bw = null;
        File csvFile= new File(appDir, userName.toLowerCase() + ".csv");
        
        try{
            bw = new BufferedWriter(new FileWriter(csvFile));
            for(Game game : gameSet){
                game.write(bw);
                bw.newLine();
            }
        }catch(IOException ex){
            System.out.println("GameList write :: error:" + ex);
        }finally{
            if(bw != null){
                try{
                    bw.flush();
                    bw.close();
                }catch(IOException e){
                    System.out.println("GameList flush  close :: error:" + e);
                }
            }
        }
    }
    
    public boolean read(){
        File appDir = App.getInstance().getAppDir();
        File csvFile = new File(appDir, userName.toLowerCase() + ".csv");
        String line;
        Pattern p = Pattern.compile("\"(.*)\""); // '"' に囲まれた文字列を抜き取る
        
        if(gameSet.size() != 0){
            System.out.println("GameList read ::  size != 0 err:" + userName);
            return false;
        }
        
        if(!csvFile.exists()){
            return false;
        }
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            
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
                game.checkUser(userName);
                addGame(gameSet, game);
            }
            
            br.close();
            return true;
            
        }catch(IOException ex){
            System.out.println("GameList read :: err:" + userName + " :: " + ex);
            return false;
        }
    }
    
    public GameListDownloader getDownloader(){
        if(userName.equals("")){
            return null;
        }
        
        int lastYear;
        int lastMonth;
        
        try{
            Game lastGame = gameSet.last();
            
            GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
            
            cal.setTime(lastGame.getDate());
            lastYear = cal.get(Calendar.YEAR);
            lastMonth = cal.get(Calendar.MONTH) + 1; /* cal.get(Calendar.MONTH) は１月が０ */
        }catch(NoSuchElementException e){
            lastYear = 1900;
            lastMonth = 1;
        }

        System.out.println("getDownloader:" + userName + " :: " + lastYear + "/" + lastMonth);
        
        return new GameListDownloader(userName, lastYear, lastMonth);
    }
}
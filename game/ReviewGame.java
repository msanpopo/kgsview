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

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import game.property.*;

public class ReviewGame extends Game {
    private Player reviewer;
    
    public ReviewGame(String sgfUrl, String reviewer, String black, String white,
            String setup, String startTime, String type, String result) {
        
        super(sgfUrl, black, white, setup, startTime, type, result);
        
        this.reviewer = new Player(reviewer);
    }
    
    public ReviewGame(ArrayList<String> l){
        super(l.get(7), l.get(2), l.get(3), l.get(4), l.get(5), l.get(0), l.get(6));
        
        this.reviewer = new Player(l.get(1));
    }
    
    @Override
    public void checkUser(String userName){
        if(black != null && black.equals(userName)){
            userColor = GoColor.BLACK;
            user = black;
        }else if(white != null && white.equals(userName)){
            userColor = GoColor.WHITE;
            user = white;
        }else if(reviewer.equals(userName)){
            user = reviewer;
        }else{
            System.out.println("ReviewGame checkUser ???????:" + userName);
        }
        
        checkUserResult();
    }
    
    @Override
    public Player[] getPlayerArray(){
        Player[] players;
        
        if(gameType == GameType.DEMO){
            players = new Player[0];
        }else{
            players = new Player[2];
            players[0] = black;
            players[1] = white;
        }
        return players;
    }
    
    @Override
    public String getPlayerString(String userName){
        String str = "";
        
        // reviewer は必ず userName と同じになるはずなので略
        if(gameType == GameType.REVIEW){
            if(!black.equals(userName)){
                str += black.getString();
            }
            
            if(!white.equals(userName)){
                str += white.getString();
            }
        }
        
        return str;
    }
    
    @Override
    public void write(Writer w){
        try{
            // System.out.println("write reviewgame :"+ gameType.getOrigString());
            w.write("\"" + gameType.getString() + "\",");
            w.write("\"" + reviewer.getString() + "\",");
            if(this.gameType == GameType.DEMO){
                // demo の時は reviewer のみ
                w.write("\"\",");
                w.write("\"\",");
            }else{
                w.write("\"" + black.getString() + "\",");
                w.write("\"" + white.getString() + "\",");
            }
            w.write("\"" + gameSetup.toString() + "\",");
            w.write("\"" + startTime.getOrigString() + "\",");
            w.write("\"" + result.getOrigString() + "\",");
            w.write("\"" + sgfFile.getOrigString() + "\"");
            
        }catch(IOException ex){
        }
    }
}
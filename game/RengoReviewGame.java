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

public class RengoReviewGame extends Game {
    private Player reviewer;
    private Player white1;
    private Player black1;
    
    public RengoReviewGame(String sgfUrl, String reviewer, String black0, String black1, String white0, String white1,
            String setup, String startTime, String type, String result) {
        
        super(sgfUrl, black0, white0, setup, startTime, type, result);
        
        this.reviewer = new Player(reviewer);
        this.white1 = new Player(white1);
        this.black1 = new Player(black1);
    }
    
    public RengoReviewGame(ArrayList<String> l){
        super(l.get(9), l.get(2), l.get(4), l.get(6), l.get(7), l.get(0), l.get(8));
        
        this.reviewer = new Player(l.get(1));
        this.white1 = new Player(l.get(3));
        this.black1 = new Player(l.get(5));
    }

    @Override
    public void checkUser(String userName){
        if(black.equals(userName)){
            userColor = GoColor.BLACK;
            user = black;
        }else if(black1.equals(userName)){
            userColor = GoColor.BLACK;
            user = black1;
        }else if(white.equals(userName)){
            userColor = GoColor.WHITE;
            user = white;
        }else if(white1.equals(userName)){
            userColor = GoColor.WHITE;
            user = white1;
        }else{
            System.out.println("Rengo:checkUser: ????????" + userName);
        }
        
        checkUserResult();
    }
    
    @Override
    public Player[] getPlayerArray(){
        Player[] players = new Player[4];
        players[0] = black;
        players[1] = black1;
        players[2] = white;
        players[3] = white1;
        return players;
    }
        
    public GoColor getColor(String userName){
        GoColor c;
        
        if(black.equals(userName) || black1.equals(userName)){
            c = GoColor.BLACK;
        }else if(white.equals(userName) || white1.equals(userName)){
            c = GoColor.WHITE;
        }else{
            c = GoColor.UNKNOWN;
        }
        return c;
    }

    @Override
    public String getPlayerString(String userName){
        StringBuilder str = new StringBuilder();
        
        if(!black.equals(userName)){
            str.append(black.getString()).append(", ");
        }
        if(!black1.equals(userName)){
            str.append(black1.getString());
        }
        
        str.append(" vs ");
        
        if(!white.equals(userName)){
            str.append(white.getString()).append(", ");
        }
        if(!white1.equals(userName)){
            str.append(white1.getString());
        }
        
        return str.toString();
    }
        
    @Override
    public void write(Writer w){
        try{
            w.write("\"" + gameType.getString() + "\",");
            w.write("\"" + reviewer.getString() + "\",");
            w.write("\"" + black.getString() + "\",");
            w.write("\"" + black1.getString() + "\",");
            w.write("\"" + white.getString() + "\",");
            w.write("\"" + white1.getString() + "\",");
            w.write("\"" + gameSetup.toString() + "\",");
            w.write("\"" + startTime.getOrigString() + "\",");
            w.write("\"" + result.getOrigString() + "\",");
            w.write("\"" + sgfFile.getOrigString() + "\"");
            
        }catch(IOException ex){
        }
    }
}
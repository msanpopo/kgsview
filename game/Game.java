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

import action.ChangeUserAction;
import action.SgfDownloadAction;
import action.SgfOpenAction;
import app.Resource;
import java.awt.Component;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;

import game.property.*;
import java.io.PrintWriter;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Game implements Comparable<Game>{
    protected SgfFile sgfFile;
    protected Player white;
    protected Player black;
    protected GameSetup gameSetup;
    protected StartTime startTime;
    protected GameType gameType;
    protected Result result;
    
    protected GoColor userColor;              // 選択されているユーザーの色
    protected UserResult userResult;        // 選択されているユーザーからみた勝敗
    protected Player user;                  // 選択されているユーザー
    
    public Game(String sgfUrl, String black, String white,
            String setup, String startTime, String type, String result){
        
        initGame(sgfUrl, black, white, setup, startTime, type, result);
    }
    
    public Game(ArrayList<String> l){
        String type = l.get(0);
        String black = l.get(1);
        String white = l.get(2);
        String setup = l.get(3);
        String startTime = l.get(4);
        String result = l.get(5);
        String sgfUrl = l.get(6);
        
        initGame(sgfUrl, black, white, setup, startTime, type, result);
    }
    
    public void initGame(String sgfUrl, String black, String white,
            String setup, String startTime, String type, String result){
        
        // System.out.println("initGame:b:"+ black + " w:" + white);
        
        this.sgfFile = new SgfFile(sgfUrl);
        
        if(white == null){
            this.white = null;
        }else{
            this.white = new Player(white);
        }
        if(black == null){
            this.black = null;
        }else{
            this.black = new Player(black);
        }
        
        this.gameSetup = new GameSetup(setup);
        this.startTime = new StartTime(startTime);
        this.gameType = GameType.get(type);
        this.result = new Result(result);
        
        this.userColor = GoColor.UNKNOWN;
        this.userResult = UserResult.UNKNOWN;
        this.user = null;
    }
    
    public void checkUser(String userName){
        if(black != null && black.equals(userName)){
            userColor = GoColor.BLACK;
            user = black;
        }else if(white != null && white.equals(userName)){
            userColor = GoColor.WHITE;
            user = white;
        }else{
            System.out.println("checkUser ???????:" + userName);
        }
        
        checkUserResult();
    }
    
    protected void checkUserResult(){
        if(result.isBlackWin()){
            if(userColor == GoColor.BLACK){
                userResult = UserResult.WIN;
            }else if(userColor == GoColor.WHITE){
                userResult = UserResult.LOSE;
            }
        }else if(result.isWhiteWin()){
            if(userColor == GoColor.BLACK){
                userResult = UserResult.LOSE;
            }else if(userColor == GoColor.WHITE){
                userResult = UserResult.WIN;
            }
        }else if(result.isJigo()){
            userResult = UserResult.JIGO;
        }
    }
    
    public GameType getGameType(){
        return gameType;
    }
    
    public boolean isGameType(GameType type){
        if(gameType == type){
            return true;
        }else{
            return false;
        }
    }
    
    public Date getDate(){
        return startTime.getDate();
    }
    
    public GoColor getUserColor(){
        return userColor;
    }
    
    public UserResult getUserResult(){
        return userResult;
    }
    
    public Rank getUserRank(){
        return user.getRank();
    }
    
    public Player[] getPlayerArray(){
        Player[] players = new Player[2];
        players[0] = black;
        players[1] = white;
        return players;
    }
    
        /*
         * Rengo でこれをどうすればいい？
         */
    public Player getOpponent(){
        if(user == black){
            return white;
        }else if(user == white){
            return black;
        }else{
            System.err.println("Game.getOpponent():");
            return null;
        }
    }
    
    public String getPlayerString(String userName){
        StringBuilder str = new StringBuilder();
        
        if(!black.equals(userName)){
            str.append(black.getString());
        }
        
        if(!white.equals(userName)){
            str.append(white.getString());
        }
        
        return str.toString();
    }
    
    public Result getResult(){
        return result;
    }
    
    public int getHandicap(){
        return gameSetup.getHandicap();
    }
    
    public int getBoardsize(){
        return gameSetup.getBoardsize();
    }
    
    public void write(PrintWriter w){
        // System.out.println("write game :"+ gameType.getOrigString());
        w.print("\"" + gameType.getString() + "\",");
        w.print("\"" + black.getString() + "\",");
        w.print("\"" + white.getString() + "\",");
        w.print("\"" + gameSetup.toString() + "\",");
        w.print("\"" + startTime.getOrigString() + "\",");
        w.print("\"" + result.getOrigString() + "\",");
        w.println("\"" + sgfFile.getOrigString() + "\"");
    }
    
    @Override
    public String toString(){
        return gameType.getString();
    }
    
    @Override
    public int compareTo(Game g){
        if(startTime.equals(g.startTime)){
            if(black.equals(g.black) && white.equals(g.white)){
                return 0;
            }else{
                // Simul は時間が全部同じになるので、時間だけで区別すると、最初以外が捨てられてしまう
                return 1;
            }
        }else{
            return startTime.compareTo(g.startTime);
        }
    }
    
    public void showPopup(Component invoker, int x, int y){
        JMenuItem sgfItem = new JMenuItem(new SgfDownloadAction(sgfFile));
        JMenuItem sgfOpenItem = new JMenuItem(new SgfOpenAction(sgfFile));
        JMenu changeUserMenu = new JMenu(Resource.get("changeUser"));
        
        for(Player player : getPlayerArray()){
            if(player == user){
                continue;
            }
            JMenuItem menuItem = new JMenuItem(new ChangeUserAction(player.getName()));
            changeUserMenu.add(menuItem);
        }
        
        JPopupMenu popup = new JPopupMenu();
        popup.add(sgfItem);
        popup.add(sgfOpenItem);
        popup.addSeparator();
        popup.add(changeUserMenu);
        popup.show(invoker, x, y);
    }
}
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

import game.ResultType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import app.Resource;

public class Result{
    // この列挙は、サーバーから帰ってくる文字列を直接変換したもの
    private static enum State {
        UNKNOWN("Unknown", "Unknown"),
        UNFINISHED("Unfinished", Resource.get("unfinished")),
        BLACKWIN("B", ""),
        WHITEWIN("W", ""),
        JIGO("Jigo", Resource.get("jigo")); // sum というユーザーに Jigo がある。flushlight というユーザーが対局数が少なくテストしやすい。
        
        // TODO
        // 無勝負もあるかもしれない。
        
        private final String str;		// 識別用文字列
        private final String message;	// 表示用文字列
        
        private State(String str, String message){
            this.str = str;
            this.message = message;
        }
        
        public static State get(String str){
            for(State state : State.values()){
                if(str.equals(state.str)){
                    return state;
                }
            }
            return UNKNOWN;
        }
            
        @Override
        public String toString(){
            return message;
        }
    };
    
    private static final Pattern p = Pattern.compile("(B|W)\\+(.*)");
    
    private String origStr;
    private State state;
    private ResultType type;
    private double point;
    
    public Result(String str){
        origStr = str;
        state = State.UNKNOWN;
        type = ResultType.UNKNOWN;
        point = 0.0;
        
        if(str.equals(State.UNFINISHED.str)){
            state = State.UNFINISHED;
        }else if(str.equals(State.JIGO.str)){	/* 一手良しにあるので追加。 持碁のことか？ */
            state = State.JIGO;
        }else {
            Matcher m = p.matcher(str);
            
            if(m.matches()){
                String bw = m.group(1);
                String pp = m.group(2);
                
                state = State.get(bw);
                type = ResultType.get(pp);
                
                if(type == ResultType.NORMAL){
                    point = Double.parseDouble(pp);
                }
            }
        }
    }
    
    public String getOrigString(){
        return origStr;
    }
    
    public boolean isUnknown(){
        if(state == State.UNKNOWN){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isFinished(){
        if(state == State.BLACKWIN || state == State.WHITEWIN || state == State.JIGO){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isBlackWin(){
        if(state == State.BLACKWIN){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isWhiteWin(){
        if(state == State.WHITEWIN){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isJigo(){
        if(state == State.JIGO){
            return true;
        }else{
            return false;
        }
    }
    
    public ResultType getType(){
        return type;
    }
    
    // ここで返す文字列に黒白どちらが勝ったかの情報は含まない（テーブルの表示用）
    @Override
    public String toString(){
        switch(state){
            case UNKNOWN:
            case UNFINISHED:
            case JIGO:
                return state.toString();
            case BLACKWIN:
            case WHITEWIN:
                if(type == ResultType.NORMAL){
                    return Double.toString(point);
                }else {
                    return type.toString();
                }
            default:
                /* ありえん */
                return "???";
        }
    }
    
    public void debugPrint(){
        System.out.println("Result:" + state + " " + type + " " + point);
    }
}
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

import app.Resource;

public enum ResultType {
    UNKNOWN("", ""),
    NORMAL("", ""), // 数え碁
    RES("Res.", Resource.get("res")),
    TIME("Time", Resource.get("time")),
    FORF("Forf.", Resource.get("forf"));
    
    private final String str;		// 識別用文字列
    private final String message;	// 表示用文字列
    
    private ResultType(String str, String message){
        this.str = str;
        this.message = message;
    }
    
    @Override
    public String toString(){
        return message;
    }
    
    public static ResultType get(String str){
        ResultType type;
        
        if(str.equals(RES.str)){
            type = RES;
        }else if(str.equals(TIME.str)){
            type = TIME;
        }else if(str.equals(FORF.str)){
            type = FORF;
        }else{
            try{
                Double.parseDouble(str);	// 文字列が数字に変換できるかどうかを例外で調べている。こんなのあり？
                type = NORMAL;
            }catch(NumberFormatException e){
                type = UNKNOWN;
            }
        }
        return type;
    }
}
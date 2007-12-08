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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameSetup{
    // str の例："19x19 H2" x の文字が文字化けするのでエックスではないみたい
    private static final Pattern p0 = Pattern.compile("(\\d+).*? H(\\d+?)");  // 置石有りの対局用
    private static final Pattern p1 = Pattern.compile("(\\d+)");				// 置石無しの対局用
    
    private int boardsize;
    private int handicap;
    
    public GameSetup(String str){
        Matcher m0 = p0.matcher(str);
        Matcher m1 = p1.matcher(str);
        
        if(m0.find()){			// 置石有り
            boardsize = Integer.parseInt(m0.group(1));
            handicap = Integer.parseInt(m0.group(2));
        }else if(m1.find()){		// 置石無し
            boardsize = Integer.parseInt(m1.group(1));
            handicap = 0;
        }else{						// ありえん
            System.out.println("GameSetup: error :" + str);
            boardsize = 0;
            handicap = 0;
        }
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        
        String size = Integer.toString(boardsize);
        
        str.append(size).append("x").append(size);
        if(handicap > 0){
            str.append(" H").append(Integer.toString(handicap));
        }
        return str.toString();
    }
    
    public int getBoardsize(){
        return boardsize;
    }
    
    public int getHandicap(){
        return handicap;
    }
}
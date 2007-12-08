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

public class Rank implements Comparable<Rank>{
    private static final Pattern p = Pattern.compile("(\\d{1,2})([kdp])(\\??)");
        /*
         * UNKNOWN	:-3
         * "-"			:-2
         * "?"			:-1
         * "30k"		:1
         * "1k"		:30
         * "1d"		:31
         * "9d"		:39
         * "1p"		:40
         * "9p"		:48
         */
    private String origStr;
    private int rank;
    private boolean isSolid;
    
    public static Rank MaxRank(){
        Rank maxRank = new Rank();
        maxRank.rank = 48;
        maxRank.isSolid = true;
        return maxRank;
    }
    
    public static Rank MinRank(){
        Rank minRank = new Rank();
        minRank.rank = -3;
        minRank.isSolid = false;
        return minRank;
    }
    
    public Rank(){
        origStr = "";
        rank = -3;
        isSolid = false;
    }
    
    public Rank(String str){
        origStr = str;
        if(str.equals("-")){
            rank = -2;
            isSolid = true;
        }else if(str.equals("?")){
            rank= -1;
            isSolid = false;
        }else{
            Matcher m = p.matcher(str);
            
            if(m.matches()){
                int n = Integer.parseInt(m.group(1));
                String kdp = m.group(2);
                
                if(kdp.equals("k")){
                    rank = 31 - n;
                }else if(kdp.equals("d")){
                    rank = n + 30;
                }else if(kdp.equals("p")){
                    rank = n + 39;
                }else{
                    rank = -3;
                    System.out.println("Rank: kdp ????:" + str);
                }
                
                if (m.groupCount() == 2){		// ? なし
                    isSolid = true;
                }else if(m.groupCount() == 3){	// ? あり
                    isSolid = false;
                }
            }else{
                isSolid = false;
                rank = -3;
                
                System.out.println("Rank:????:" + str);
            }
        }
    }
    
    // ? や ｰ や Unknown まで比較してしまっているのは問題かも
    @Override
    public int compareTo(Rank rank){
        int val;
        
        if(this.rank < rank.rank){
            val = -1;
        }else if(this.rank == rank.rank){
            val = 0;
        }else{
            val = 1;
        }

        return val;
    }
    
    @Override
    public String toString(){
        return origStr;
    }
    
    public int getRankNumber(){
        return rank;
    }
    
    public boolean isUnknown(){
        if(rank == -3){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isSolid(){
        return isSolid;
    }
    
    public void debugPrint(){
        System.out.println("Rank:" + rank);
    }
}
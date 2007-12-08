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

import action.ChangeUserAction;
import app.Resource;
import java.awt.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class Player{
    private static final Pattern p = Pattern.compile("(.*) \\[(.*)\\]");	// ランクつき（ゲスト以外）
    private static final Pattern pg = Pattern.compile("(.*)");				// ランクなし（ゲスト）
    
    private String name;
    private Rank rank;
    
    public Player(String str){
        Matcher m = p.matcher(str);
        Matcher mg = pg.matcher(str);
        
        if(m.matches()){			// ランクつき
            name = m.group(1);
            rank = new Rank(m.group(2));
        }else if(mg.matches()){	// ランクなし
            name = mg.group(1);
            rank = new Rank();	// ランクなしは "Unknown" なランクを持つ
        }else{						// ありえん
            name = "";
            rank = new Rank();
            System.out.println("player: ??? str:" + str);
        }
    }
    
    public boolean equals(String name){
        if(this.name.equalsIgnoreCase(name)){
            return true;
        }else{
            return false;
        }
    }
    
    public String getName(){
        return name;
    }
    
    public Rank getRank(){
        return rank;
    }
    
    public String getString(){
        StringBuilder str = new StringBuilder();
        
        str.append(name);
        if(rank.isUnknown() == false){
            str.append(" [").append(rank.toString()).append("]");
        }
        return str.toString();
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public void debugPrint(){
        System.out.print("player: name:" + name + "  :");
        rank.debugPrint();
    }
    
    public void showPopup(Component invoker, int x, int y){
        JMenuItem menuItem = new JMenuItem(new ChangeUserAction(name));
        
        JMenu menu = new JMenu(Resource.get("changeUser"));
        menu.add(menuItem);
        
        JPopupMenu popup = new JPopupMenu();
        popup.add(menu);
        popup.show(invoker, x, y);
    }
}
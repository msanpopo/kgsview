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

package app;

import javax.swing.DefaultComboBoxModel;

/*
 *コンボボックスに表示されるユーザー名のリスト
 */
@SuppressWarnings("serial")
public class MarkList extends DefaultComboBoxModel{
    public MarkList() {
        addElement("");
    }
    
    // ”aaa,bbb,ccc"
    public void setConfString(String str){
        String[] list = str.split(",");
        
        for(String s: list){
            if(s.length() != 0){
                addElement(s);
            }
        }
    }
    
    public String getConfString(){
        int n = getSize();
        StringBuilder str = new StringBuilder();
        
        for(int i = 0; i < n; ++i){
            String name = (String)getElementAt(i);
            
            // System.out.println("MarkList.getConfString:name:" + name + ":");
            
            if(name.equals("")){
                continue;
            }
            
            if(str.length() != 0){
                str.append(",");
            }
            str.append(name);
        }
        
        return str.toString();
    }
    
    public void add(String user){
        if(find(user) == -1){
            System.out.println(this.getClass() + ":add:" + user);
            addElement(user);
        }
    }
    
    public void remove(String user){
        int index = find(user);
        if(index != -1){
            System.out.println(this.getClass() + ":remove:" + user);
            removeElementAt(index);
        }
    }
    
    private int find(String user){
        int n = getSize();
                            
        for(int i = 0; i < n; ++i){
            String name = (String)getElementAt(i);
            
            if(user.equals(name)){
                return i;
            }
        }
        
        return -1;
    }
}
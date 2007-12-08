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

package statistics.filter;

import game.Game;
import java.util.ArrayList;
import java.util.Collection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AndFilter extends GameFilter{
    private ArrayList<GameFilter> list;
    
    public AndFilter() {
        super();
        list = new ArrayList<GameFilter>();
    }
    
    public Collection<GameFilter> getCollection(){
        return list;
    }
    
    public void add(GameFilter filter){
        list.add(filter);
    }
    
    @Override
    public GameFilter copy() {
        AndFilter filter = new AndFilter();
        filter.name = name + "(copy)";
        filter.marked = marked;
        for(GameFilter f : list){
            filter.list.add(f.copy());
        }
        
        return filter;
    }
    
    @Override
    public boolean checkGame(Game game) {
        boolean retval = false;
        
        for(GameFilter f : list){
            retval = f.checkGame(game);
                       
            if(retval == false){
                break;
            }
        }
        return retval;
    }
    
    @Override
    protected void readXmlLocal(Element e) {
        String nodeName = e.getNodeName();

        try {
            GameFilter filter = (GameFilter) this.getClass().getClassLoader().loadClass(nodeName).newInstance();
            filter.readXml(e);
            
            list.add(filter);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        
        System.out.println("AdnFilter.readXml:" + nodeName);
    }
    
    @Override
    protected void readXmlFinished() {
    }
    
    @Override
    protected void writeXmlLocal(Document doc, Element e) {
        for(GameFilter f : list){
            e.appendChild(f.writeXml(doc));
        }
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("AndFilter").append("\n");
        str.append("name    :" + name).append("\n");
        str.append("marked  :" + marked).append("\n");
        for(GameFilter f : list){
            str.append(f.toString());
        }
        return str.toString();
    }
}
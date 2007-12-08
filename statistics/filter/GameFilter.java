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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public abstract class GameFilter {
    protected String name;
    protected boolean marked;
    
    public GameFilter(){
        name = "";
        marked = false;
    }
    
    public void readXml(Node node){
        Node child = node.getFirstChild();
        
        while(child != null){
            if(child.getNodeType() == Node.ELEMENT_NODE){
                Element e = (Element)child;
                
                String nodeName = e.getNodeName();
                
                String str = getString(e);
                
                if(nodeName.equals("name")){
                    name = str;
                    
                }else if(nodeName.equals("marked")){
                    marked = Boolean.parseBoolean(str);
                    
                }else{
                    readXmlLocal(e);
                }
                
            }
            child = child.getNextSibling();
        }
        
        readXmlFinished();
    }
    
    protected String getString(Node node){
        Node child = node.getFirstChild();
        if(child != null && child.getNodeType() == Node.TEXT_NODE){
            String text = child.getNodeValue();
            return text;
        }
        return "";
    }
    
    public Node writeXml(Document doc){
        Element e = doc.createElement(this.getClass().getName());
        
        Element nameElement = doc.createElement("name");
        Text nameText = doc.createTextNode(name);
        nameElement.appendChild(nameText);
        
        Element markedElement = doc.createElement("marked");
        Text markedText = doc.createTextNode(Boolean.toString(marked));
        markedElement.appendChild(markedText);
        
        e.appendChild(nameElement);
        e.appendChild(markedElement);
        
        writeXmlLocal(doc, e);
        
        return e;
    }
    
    public abstract GameFilter copy();
    public abstract boolean checkGame(Game game);
    protected abstract void readXmlLocal(Element e);
    protected abstract void readXmlFinished();
    protected abstract void writeXmlLocal(Document doc, Element e);

    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public boolean isMarked(){
        return marked;
    }
    
    public void setMarked(boolean bool){
        System.out.println("GameFilter.setMarked:" + bool);
        marked = bool;
    }
}
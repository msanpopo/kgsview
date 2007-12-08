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
import game.GameType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class TypeFilter extends GameFilter{
    private boolean ranked;
    private boolean free;
    private boolean teaching;
    private boolean demo;
    private boolean review;
    private boolean simul;
    private boolean tournament;
    private boolean rengo;
    private boolean rengoReview;
    
    public TypeFilter() {
        super();
        
        this.ranked = false;
        this.free = false;
        this.teaching = false;
        this.demo = false;
        this.review = false;
        this.simul = false;
        this.tournament = false;
        this.rengo = false;
        this.rengoReview = false;
    }
    
    @Override
    public GameFilter copy(){
        TypeFilter filter = new TypeFilter();
        filter.name = name + "(copy)";
        filter.marked = marked;
        filter.ranked = ranked;
        filter.free = free;
        filter.teaching = teaching;
        filter.demo = demo;
        filter.review = review;
        filter.simul = simul;
        filter.tournament = tournament;
        filter.rengo = rengo;
        filter.rengoReview = rengoReview;
        
        return filter;
    }
    
    public boolean isSelected(GameType type){
        switch(type){
            case RANKED:
                return ranked;
            case FREE:
                return free;
            case TEACHING:
                return teaching;
            case DEMO:
                return demo;
            case REVIEW:
                return review;
            case SIMUL:
                return simul;
            case TOURNAMENT:
                return tournament;
            case RENGO:
                return rengo;
            case RENGO_REVIEW:
                return rengoReview;
            default:
                System.err.println("error:TypeFilter.getSelected():" + type.toString());
                return false;
        }
    }
    
    public void setSelected(GameType type, boolean bool){
        //System.out.println("TypeFilter.setSelected:" + type + ":" + bool);
        switch(type){
            case RANKED:
                ranked = bool;
                break;
            case FREE:
                free = bool;
                break;
            case TEACHING:
                teaching = bool;
                break;
            case DEMO:
                demo = bool;
                break;
            case REVIEW:
                review = bool;
                break;
            case SIMUL:
                simul = bool;
                break;
            case TOURNAMENT:
                tournament = bool;
                break;
            case RENGO:
                rengo = bool;
                break;
            case RENGO_REVIEW:
                rengoReview = bool;
                break;
            default:
                System.err.println("error:TypeFilter.setSelected():" + type.toString() + ":" + bool);
                break;
        }
    }
    
    @Override
    public boolean checkGame(Game game) {
        return isSelected(game.getGameType());
    }
    
    @Override
    public void readXmlLocal(Element e) {
        String typeName = e.getNodeName();
        
        String s = typeName.replace("_", " ");
        GameType type = GameType.get(s);
        boolean bool = getBool(e);
        
        // System.out.println("GameFilter.readXml:" + name + " " + bool);
        switch(type){
            case RANKED:
                ranked = bool;
                break;
            case FREE:
                free = bool;
                break;
            case TEACHING:
                teaching = bool;
                break;
            case DEMO:
                demo = bool;
                break;
            case REVIEW:
                review = bool;
                break;
            case SIMUL:
                simul = bool;
                break;
            case TOURNAMENT:
                tournament = bool;
                break;
            case RENGO:
                rengo = bool;
                break;
            case RENGO_REVIEW:
                rengoReview = bool;
                break;
            default:
                
        }
    }
    
    @Override
    public void readXmlFinished(){
    }
    
    private boolean getBool(Node node){
        Node child = node.getFirstChild();
        while(child != null){
            if(child.getNodeType() == Node.TEXT_NODE){
                String text = child.getNodeValue();
                return Boolean.valueOf(text);
            }
        }
        System.err.println("error:getBool:" + child);
        return false;
    }
    
    @Override
    public void writeXmlLocal(Document doc, Element e) {
        e.appendChild(createBoolElement(doc, GameType.RANKED, ranked));
        e.appendChild(createBoolElement(doc, GameType.FREE, free));
        e.appendChild(createBoolElement(doc, GameType.TEACHING, teaching));
        e.appendChild(createBoolElement(doc, GameType.DEMO, demo));
        e.appendChild(createBoolElement(doc, GameType.REVIEW, review));
        e.appendChild(createBoolElement(doc, GameType.SIMUL, simul));
        e.appendChild(createBoolElement(doc, GameType.TOURNAMENT, tournament));
        e.appendChild(createBoolElement(doc, GameType.RENGO, rengo));
        e.appendChild(createBoolElement(doc, GameType.RENGO_REVIEW, rengoReview));
    }
    
    private Element createBoolElement(Document doc, GameType type, boolean bool){
        //System.out.println("####" + type.getString() + ":" + bool);
        Element ele = doc.createElement(type.getXmlString());
        Text text= doc.createTextNode(Boolean.toString(bool));
        
        ele.appendChild(text);
        
        return ele;
    }
    
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("TypeFilte").append("\n");
        str.append("name    :" + name).append("\n");
        str.append("marked  :" + marked).append("\n");
        str.append("ranked  :" + ranked).append("\n");
        str.append("free    :" + free).append("\n");
        str.append("teaching:" + teaching).append("\n");
        str.append("demo    :" + demo).append("\n");
        str.append("simul   :" + simul).append("\n");
        str.append("tour    :" + tournament).append("\n");
        str.append("rengo   :" + rengo).append("\n");
        str.append("rengo r :" + rengoReview).append("\n");
        return str.toString();
    } 
}
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

import app.Resource;
import game.GameType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.table.AbstractTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class FilterList extends AbstractTableModel {
    private static final String FILTER_XML = "filter.xml";
    
    private File xmlFile;
    private ArrayList<GameFilter> filterList;
    
    public FilterList(File appDir) {
        xmlFile = new File(appDir, FILTER_XML);
        
        filterList = new ArrayList<GameFilter>();
        
        readXml();
    }
    
    public Collection<GameFilter> getCollection(){
        return filterList;
    }
    
    public ArrayList<String> getNameArray(){
        ArrayList<String> array = new ArrayList<String>();
        
        for(GameFilter f : filterList){
            String name = f.getName();
            if(name == null || name.isEmpty()){
                continue;
            }
            array.add(f.getName());
        }
        return array;
    }
    
    public GameFilter find(String name){
        if(name == null || name.isEmpty()){
            return null;
        }
        
        for(GameFilter f : filterList){
            if(name.equals(f.getName())){
                return f;
            }
        }
        
        return null;
    }
    
    public GameFilter get(int index){
        return filterList.get(index);
    }
    
    public void update(){
        fireTableDataChanged();
    }

    public void add(GameFilter filter){
        filterList.add(filter);
        fireTableDataChanged();
    }
    
    public void remove(GameFilter filter){
        filterList.remove(filter);
        fireTableDataChanged();
    }

    public void replace(GameFilter oldFilter, GameFilter newFilter){
        int index = filterList.indexOf(oldFilter);
        filterList.set(index, newFilter);
        fireTableDataChanged();
    }
    
    private void setDefaultFilter(){
        TypeFilter defaultFilter = new TypeFilter();
        defaultFilter.setName("Default(R F S T)");
        defaultFilter.setSelected(GameType.RANKED, true);
        defaultFilter.setSelected(GameType.FREE, true);
        defaultFilter.setSelected(GameType.SIMUL, true);
        defaultFilter.setSelected(GameType.TOURNAMENT, true);
        defaultFilter.setMarked(true);
        
        TypeFilter record = new TypeFilter();
        record.setName("Ranked");
        record.setSelected(GameType.RANKED, true);
        
        filterList.add(defaultFilter);
        filterList.add(record);
    }
    
    public void readXml(){
        System.out.println("FilterList.readXml");
        
        if(xmlFile.exists() == false){
            setDefaultFilter();
            
            return;
        }
        
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = dbfactory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            Element root = doc.getDocumentElement();
            
            Node node = root.getFirstChild();
            while(node != null){
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    String name = node.getNodeName();
                    // System.out.println("node value:" + name);
                    try {
                        GameFilter filter = (GameFilter) this.getClass().getClassLoader().loadClass(name).newInstance();
                        filter.readXml(node);
                        filterList.add(filter);
                    } catch (InstantiationException ex) {
                        ex.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    
                    
                }
                node = node.getNextSibling();
            }

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            System.err.println("error:FilterList.readXML:" + ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("error:FilterList.readXML:" + ex);
        } catch (SAXException ex) {
            ex.printStackTrace();
            System.err.println("error:FilterList.readXML:" + ex);
        }
    }
    
    public void writeXml(){
        System.out.println("FilterList.writeXml");
        
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        
        try {
            builder = dbfactory.newDocumentBuilder();
            doc = builder.newDocument();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            System.err.println("error:FilterList.readXML:" + ex);
            return;
        }
        
        Element root = doc.createElement("kgsview");
        
        doc.appendChild(root);
      
        for(GameFilter f : filterList){
            root.appendChild(f.writeXml(doc));
        }
        root.appendChild(doc.createTextNode("\n"));
        
        DOMSource src = new DOMSource();
        src.setNode(doc);
        
        StreamResult target = new StreamResult(xmlFile);
        
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            try {
                transformer.transform(src, target);
                // transformer.transform(src, new StreamResult(System.out));
            } catch (TransformerException ex) {
                ex.printStackTrace();
            }
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerFactoryConfigurationError ex) {
            ex.printStackTrace();
        }
    }
  
    /////////
    private static final int COLUMN_SIZE = 2;
    private static final String[] header = {
        Resource.get("show"),
        Resource.get("filterName")
    };
    
    @Override
    public String getColumnName(int col) {
        return header[col];
    }
        
    @Override
    public int getRowCount() {
        return filterList.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_SIZE;
    }

    @Override
    public Class<?> getColumnClass(int c) {
        if(filterList.size() == 0){
            return String.class;         //  rowList 中身がない時にヘッダーを触ってソートすると落ちるので、その場しのぎに逃げる。
        }else{
            return getValueAt(0, c).getClass();
        }
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        if(col == 0){
            GameFilter filter = filterList.get(row);
            filter.setMarked((Boolean)value);
            fireTableCellUpdated(row, col);
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int col) {
        if (col == 0) {
            return true;
        } else {
            return false;
        }
    }
        
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GameFilter filter = filterList.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return filter.isMarked();
            case 1:
                return filter.getName();
            default:
                return "???";
        }
    }
}
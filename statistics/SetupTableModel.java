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

package statistics;

import app.Resource;
import game.Game;
import game.GoColor;

public class SetupTableModel extends SimpleTableModel{
    private static final String[]  b = {
        Resource.get("19x19") + " " + Resource.get("black") + " H0",
        "",
        Resource.get("19x19") + " " + Resource.get("black") + " H2",
        Resource.get("19x19") + " " + Resource.get("black") + " H3",
        Resource.get("19x19") + " " + Resource.get("black") + " H4",
        Resource.get("19x19") + " " + Resource.get("black") + " H5",
        Resource.get("19x19") + " " + Resource.get("black") + " H6",
        Resource.get("19x19") + " " + Resource.get("black") + " H7-"
    };
    private static final String[]  w = {
        Resource.get("19x19") + " " + Resource.get("white") + " H0",
        "",
        Resource.get("19x19") + " " + Resource.get("white") + " H2",
        Resource.get("19x19") + " " + Resource.get("white") + " H3",
        Resource.get("19x19") + " " + Resource.get("white") + " H4",
        Resource.get("19x19") + " " + Resource.get("white") + " H5",
        Resource.get("19x19") + " " + Resource.get("white") + " H6",
        Resource.get("19x19") + " " + Resource.get("white") + " H7-"
    };
    
    private ResultRow[] rsB19;
    private ResultRow[] rsW19;
    private ResultRow rsA09;
    private ResultRow rsA00;
    
    private ResultRow all19;
    private ResultRow all19b;
    private ResultRow all19w;
    private ResultRow all19bh;
    private ResultRow all19wh;
    
    public SetupTableModel() {
        super();
        
        rsB19 = new ResultRow[8];
        rsW19 = new ResultRow[8];
        
        for(int i = 0; i < rsB19.length; ++i){
            if(i == 1){
                continue;
            }
            rsB19[i] = new ResultRow(b[i]);
            addRow(rsB19[i]);
        }
        for(int i = 0; i < rsW19.length; ++i){
            if(i == 1){
                continue;
            }
            rsW19[i] = new ResultRow(w[i]);
            addRow(rsW19[i]);
        }
        
        rsA09 = new ResultRow(Resource.get("9x9"));
        rsA00 = new ResultRow(Resource.get("otherSize"));
        
        all19 = new ResultRow(Resource.get("19x19_all"));
        all19b = new ResultRow(Resource.get("19x19_b_all"));
        all19w = new ResultRow(Resource.get("19x19_w_all"));
        all19bh = new ResultRow(Resource.get("19x19_b_withHandicap"));
        all19wh = new ResultRow(Resource.get("19x19_w_withHandicap"));
        
        setTableName("setup");
        
        addRow(rsA09);
        addRow(rsA00);
        
        addRow(all19);
        addRow(all19b);
        addRow(all19w);
        addRow(all19bh);
        addRow(all19wh);
    }
    
    @Override
    public void addGame(Game game){
        int handicap = game.getHandicap();
        GoColor color = game.getUserColor();
        int boardsize = game.getBoardsize();
        
        if(boardsize == 19){
            // System.out.println("SimpleTableModel :" + color);
            if(color == GoColor.BLACK){
                // System.out.println("SimpleTableModel b");
                if(handicap > 6){
                    rsB19[7].addGame(game);
                }else{
                    rsB19[handicap].addGame(game);
                }
                if(handicap != 0){
                    all19bh.addGame(game);
                }
                all19b.addGame(game);
            }else if(color == GoColor.WHITE){
                // System.out.println("SimpleTableModel w");
                if(handicap > 6){
                    rsW19[7].addGame(game);
                }else{
                    rsW19[handicap].addGame(game);
                }
                if(handicap != 0){
                    all19wh.addGame(game);
                }
                all19w.addGame(game);
            }
            all19.addGame(game);
        }else if(boardsize == 9){
            rsA09.addGame(game);
        }else{
            rsA00.addGame(game);
        }
    }
}

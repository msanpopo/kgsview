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

import game.Game;
import game.GameType;

public class TypeTableModel extends SimpleTableModel{
    private ResultRow ranked;
    private ResultRow free;
    private ResultRow simul;
    private ResultRow tournament;
    private ResultRow rengo;
    private ResultRow teaching;
    private ResultRow demonstration;
    private ResultRow review;
    private ResultRow rengoReview;
    
    public TypeTableModel() {
        super();
        
        ranked = new ResultRow("Ranked");
        free = new ResultRow("Free");
        simul = new ResultRow("Simul");
        tournament = new ResultRow("Tournament");
        rengo = new ResultRow("Rengo");
        teaching = new ResultRow("Teaching");
        demonstration = new ResultRow("Demonstration");
        review = new ResultRow("Review");
        rengoReview = new ResultRow("Rengo Review");
        
        setTableName("type");
        
        addRow(ranked);
        addRow(free);
        addRow(simul);
        addRow(tournament);
        addRow(rengo);
        addRow(teaching);
        // addRow(demonstration);
        // addRow(review);
        // addRow(rengoReview);
    }
    
    @Override
    public void addGame(Game game){
        GameType type = game.getGameType();
        
        switch(type){
            case RANKED:
                ranked.addGame(game);
                break;
            case FREE:
                free.addGame(game);
                break;
            case SIMUL:
                simul.addGame(game);
                break;
            case TOURNAMENT:
                tournament.addGame(game);
                break;
            case RENGO:
                rengo.addGame(game);
                break;
            case TEACHING:
                teaching.addGame(game);
                break;
            case DEMO:
                demonstration.addGame(game);
                break;
            case REVIEW:
                review.addGame(game);
                break;
            case RENGO_REVIEW:
                rengoReview.addGame(game);
                break;
            default:
        }
    }
}
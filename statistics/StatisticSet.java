/*
 * KGSview - KGS(ネット碁会所)用戦績表示アプリケーション
 *
 * Copyright (C) 2006 sanpo
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

import app.App;
import app.Config;
import app.KgsConfigEnum;
import archive.Archive;
import game.Game;
import game.GameTableModel;
import statistics.filter.FilterList;
import statistics.filter.GameFilter;

public class StatisticSet {
    public CalendarTable calendar;
    public GameTableModel game;
    public TypeTableModel type;
    public SetupTableModel setup;
    public RecordTableModel record;
    public MonthTableModel month;
    public OpponentTableModel opponent;
    public CustomTableModel custom;
    
    private GameFilter gameFilter;
    private GameFilter typeFilter;
    private GameFilter setupFilter;
    private GameFilter recordFilter;
    private GameFilter monthFilter;
    private GameFilter opponentFilter;
    
    public StatisticSet(){
        calendar = new CalendarTable();
        game = new GameTableModel();
        type = new TypeTableModel();
        setup = new SetupTableModel();
        record = new RecordTableModel();
        month = new MonthTableModel();
        opponent = new OpponentTableModel();
        custom = new CustomTableModel();
        
        gameFilter = null;
        typeFilter = null;
        setupFilter = null;
        recordFilter = null;
        monthFilter = null;
        opponentFilter = null;
    }
    
    public void resetFilter(){
        Config config = App.getInstance().getConfig();
        FilterList fl = App.getInstance().getFilterList();
        
        gameFilter = fl.find(config.getProperty(KgsConfigEnum.FILTER_GAME_LIST));
        typeFilter = fl.find(config.getProperty(KgsConfigEnum.FILTER_TYPE));
        setupFilter = fl.find(config.getProperty(KgsConfigEnum.FILTER_SETUP));
        recordFilter = fl.find(config.getProperty(KgsConfigEnum.FILTER_RECORD));
        monthFilter = fl.find(config.getProperty(KgsConfigEnum.FILTER_MONTH));
        opponentFilter = fl.find(config.getProperty(KgsConfigEnum.FILTER_OPPONENT));
    }
    
    public void rebuild(){
        custom.build();
    }
    
    public void renew(Archive archive){
        System.out.println("StatisticSet.renew()");
        
        int n = archive.getSize();
        
        Game[] gameArray = new Game[n];
        
        calendar.setArchive(archive);
        
        type.clear();
        setup.clear();
        record.clear();
        month.clear();
        opponent.clear();
        custom.clear();
        
        int i = 0;
        for(Game g : archive.getCollection()){
            if(checkFilter(gameFilter, g)){
                gameArray[i] = g;
                ++i;
            }
            
            if(checkFilter(typeFilter, g)){
                type.addGame(g);
            }
            
            if(checkFilter(recordFilter, g)){
                record.addGame(g);
            }
            
            custom.addGame(g);
            
            if(checkFilter(setupFilter, g)){
                setup.addGame(g);
            }
            
            if(checkFilter(monthFilter, g)){
                month.addGame(g);
            }
            
            if(checkFilter(opponentFilter, g)){
                opponent.addGame(g);
            }
        }
        
        Game[] reversedArray = reverse(gameArray);
        game.setGameArray(reversedArray, archive.getName());
        
        type.finish();
        setup.finish();
        record.finish();
        month.finish();
        opponent.finish();
        custom.finish();
    }
    
    private Game[] reverse(Game[] array){
        int n = array.length;
        Game[] newArray = new Game[n];
        for(int i = 0; i < n; ++i){
            newArray[n - 1 - i] = array[i];
        }
        return newArray;
    }
    
    private boolean checkFilter(GameFilter filter, Game game){
        if(filter == null){
            return true;
        }else{
            return filter.checkGame(game);
        }
    }
}
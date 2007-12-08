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

package action;

import app.KgsConfig;
import game.Game;
import game.GameList;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import action.util.GameListDownloader;
import app.App;
import app.Resource;
import javax.swing.SwingWorker;
import ui.DownloadDialog;

/*
 * 指定された GameList を更新する
 */
@SuppressWarnings("serial")
public class GameListUpdateAction  extends AbstractAction{
    private GameList gameList;
    private DownloadDialog dialog;
    private GameListDownloader downloader;
            
    public GameListUpdateAction(){
        init(null);
    }
    
    public GameListUpdateAction(GameList gameList){
        init(gameList);
    }
    
    private void init(GameList list){
        this.gameList = list;
        
        ClassLoader cl = this.getClass().getClassLoader();
        Icon icon  = new ImageIcon(cl.getResource("icon/stock_refresh.png"));
        
        putValue(Action.NAME, Resource.get("update"));
        putValue(Action.ACTION_COMMAND_KEY, "GameListUpdate");
        putValue(Action.SMALL_ICON, icon);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("GameListUpdateAction:performed:" + e);
        
        gameList = App.getInstance().getGameList();
        doAction();
    }
    
    public void doAction(){
        downloader = gameList.getDownloader();
        
        if(downloader == null){
            return;
        }
        
        dialog = new DownloadDialog(downloader);
        dialog.resetProgressBar(0);
        dialog.setInfoText(Resource.get("downloadMonthTable"));
        
        downloader.setOldAccount(App.getInstance().getConfig().getBooleanProperty(KgsConfig.OLD_ACCOUNT));
        downloader.setDialog(dialog);
        downloader.addPropertyChangeListener(dialog);
        downloader.addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName()) && SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    dialog.setVisible(false);
                    dialog.dispose();
                }
            }
        });
        downloader.execute();
        
        dialog.setVisible(true);
        // 進捗ダイアログがモーダルで、スレッド終了でダイアログの閉じるので、実際にはここでとまる。

        ArrayList<Game> newGameArray = downloader.getGameArray();
                
        if(newGameArray.size() == 0){
            System.out.println("GameListUpdateAction : download : newGameList.size() == 0");
            return;
        }
        
        gameList.addGameList(newGameArray);
        gameList.write();
        
        App.getInstance().setGameList(gameList);
    }
}
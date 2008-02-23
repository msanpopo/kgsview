/*
 * KGSview - KGS(ネット碁会所)用戦績表示アプリケーション
 *
 * Copyright (C) 2008 sanpo
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

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;

import archive.Downloader;
import app.App;
import app.Resource;
import archive.Archive;
import javax.swing.SwingWorker;
import ui.DownloadDialog;

/*
 * 現在の Archive のカレンダー中でダウンロードマークの付いた月のデータをダウンロードする
 */
public class DownloadAction  extends AbstractAction{
    private Archive archive;
    private DownloadDialog dialog;
    private Downloader downloader;
            
    public DownloadAction(){
//        ClassLoader cl = this.getClass().getClassLoader();
//        Icon icon  = new ImageIcon(cl.getResource("icon/stock_refresh.png"));
        
        putValue(Action.NAME, Resource.get("download"));
//        putValue(Action.SMALL_ICON, icon);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("GameListUpdateAction:performed:" + e);
        
        App app = App.getInstance();
        
        archive = app.getArchive();
        downloader = new Downloader(archive);

        dialog = new DownloadDialog(downloader);
        dialog.resetProgressBar(0);
        //dialog.setInfoText(Resource.get("downloadMonthTable"));
        
        downloader.setOldAccount(app.getConfig().getBooleanProperty(KgsConfig.OLD_ACCOUNT));
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
        // 進捗ダイアログがモーダルで、スレッド終了でダイアログを閉じるので、実際にはここでとまる。

        Archive newArchive = downloader.getArchive();

        newArchive.write();

        App.getInstance().setArchive(newArchive);
    }
}
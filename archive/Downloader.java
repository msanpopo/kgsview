/*
 * KGSview - KGS(ネット碁会所)用戦績表示アプリケーション
 *
 * Copyright (C) 2006, 2007, 2008 sanpo
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

package archive;

import app.App;
import app.KgsConfigEnum;
import java.util.List;
import javax.swing.SwingWorker;

import ui.DownloadDialog;

public class Downloader extends SwingWorker<Archive, String>{
    private final Archive archive;
    private boolean oldAccount;
    
    private DownloadDialog dialog;

    private boolean canceled;
    
    public Downloader(Archive archive) {
        this.archive = archive;
        this.oldAccount = App.getInstance().getConfig().getBooleanProperty(KgsConfigEnum.OLD_ACCOUNT);
        this.canceled = false;
    }
    
    public void setDialog(DownloadDialog dialog){
        this.dialog = dialog;
    }
    
    public void cancel(){
        canceled = true;
    }
    
    public boolean isCanceled(){
        return canceled;
    }
    
    @Override
    protected void process(List<String> chunks){
        int size = chunks.size();
        
        dialog.setInfoText(chunks.get(size - 1));
    }
    
    public Archive doInBackground() {
        archive.download(this);
        archive.write();
        
        return archive;
    }
    
    @Override
    protected void done(){
        System.out.println("Downloader.done");
        
        if(dialog != null){
            dialog.setVisible(false);
            dialog.dispose();
        }
        App.getInstance().setArchive(archive);
    }
    
    public void setDownloadStatus(String text){
        publish(text); // publish は作業結果（例えばダウンロードした結果の一部）を返すのが本当みたいな気がする
    }
}
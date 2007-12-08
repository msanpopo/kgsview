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

package action.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;

import app.Resource;

import game.Game;
import javax.swing.SwingWorker;
import ui.DownloadDialog;

public class GameListDownloader extends SwingWorker<ArrayList<Game>, String>{
    private String name;
    private boolean oldAccount;
    private int year;
    private int month;
    
    private ArrayList<Game> gameArray;
    
    private DownloadDialog dialog;
    
    private int nCurrent;
    private boolean canceled;
    
    Resource resource;
    
    public GameListDownloader(String name, int year, int month) {
        resource = Resource.getInstance();
        
        this.name = name;
        this.oldAccount = false;
        this.year = year;
        this.month = month;
        
        this.gameArray = null;
        
        nCurrent = 0;
        canceled = false;
        System.out.println("gld :" + name + " y:" + year + " m:" + month);
    }

    public void setOldAccount(boolean bool){
        oldAccount = bool;
    }
    
    public void setDialog(DownloadDialog dialog){
        this.dialog = dialog;
    }
    
    public void cancel(){
        canceled = true;
    }
    
    public ArrayList<Game> getGameArray(){
        try {
            gameArray = get();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        
        return gameArray;
    }
    
    @Override
    protected void process(List<String> chunks){
        int size = chunks.size();
        
        dialog.setInfoText(chunks.get(size - 1));
    }
    
    @Override
    public ArrayList<Game> doInBackground() {
        return doDownload();
    }
    
    private  ArrayList<Game> doDownload() {
        Html html = new Html(name, oldAccount);
        ArrayList<Game> gameListTmp = new ArrayList<Game>();

        html.download(this);      // 年と月を指定せずにもっとも最近の月のデータを取得
      
        if(html.hasMonthTable() == true){
            ArrayList<Html> downloadList = new ArrayList<Html>();
            
            for (Html h : html.getMonthList()) {
                if (h.compare(this.year, this.month) >= 0) {
                    System.out.println("doDownload : create month list :y" + h.toString());
                    
                    downloadList.add(h);
                }
            }

            if(downloadAllMonth(html, downloadList, gameListTmp) == true){
                if(html.hasGameTable()){
                    gameListTmp.addAll(html.getGameList());
                }
            }else{
                String message = Resource.get("downloadIsNotFinished");
                JOptionPane.showMessageDialog(dialog, message, Resource.get("warning"), JOptionPane.WARNING_MESSAGE);
                gameListTmp.clear();
            }

        }else{
            String message = "\"" + name + "\"" + Resource.get("sDataNotFound");
            JOptionPane.showMessageDialog(dialog, message, Resource.get("info"), JOptionPane.INFORMATION_MESSAGE);
        }
        
        return gameListTmp;
    }
    
    private boolean downloadAllMonth(Html html, ArrayList<Html> downloadList, ArrayList<Game> gameListTmp) {
        /*
        if(downloadList.size() != 0){
            Object[] options = { Resource.get("yes"), Resource.get("no") };
            
            String message = downloadList.size() + "ヶ月のデータをダウンロードします。\n数分かかるかもしれません。\nよろしいですか？";
            
            int n = JOptionPane.showOptionDialog(dialog, message, Resource.get("question"), JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
            if(n == JOptionPane.NO_OPTION){
                return false;
            }
        }
        */
        
        // downloadList の中に直近月はない
        for (Html h : downloadList) {
            int nDownload = downloadList.size();
            nCurrent += 1;
            
            String msg = Resource.get("downloading") + " " + nCurrent + " / " + nDownload;
            publish(msg); // publish は作業結果（例えばダウンロードした結果の一部）を返すのが本当みたいな気がする
            
            if(canceled == true){
                System.out.println("download canceled :");
                return false;
            }
            
            System.out.println("download :" + h.toString());
            h.download(this);
            
            if(h.hasGameTable()){
                gameListTmp.addAll(h.getGameList());
            }
            
            try {
                // 2ch で kgs の管理者がこのようなツールを作る時はダウンロード間隔３秒以上あけるように言ってたという
                // 書き込みを見たのでそれにならう。
                
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                System.err.println("GameListDownloader:sleep():" + ex);
                return false;
            }
        }
        return true;
    }
}
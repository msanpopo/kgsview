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

package app;

import java.io.File;
import java.util.TimeZone;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import statistics.StatisticSet;
import statistics.filter.FilterList;
import ui.MainWindow;
import archive.Archive;

public class App{
    private static App appData;
    
    private static final String APP_DIR_LINUX = ".kgsview";
    private static final String APP_DIR_WINDOWS = "kgsview";
    private static final String SGF_DIR = "sgf";
    private static final String KGSVIEW_CONF = "kgsview.conf";
    
    private File appDir;
    private File cfgFile;
    private File sgfDir;
    
    private Config config;
    
    private Archive archive;
    
    private StatisticSet statisticSet;
    private MarkList markList;
    private MainWindow window;
    
    private FilterList filterList;
    
    public static App getInstance(){
        if(appData == null){
            appData = new App();
        }
        return appData;
    }
    
    private App() {
        String appDirTmp;
        String os = System.getProperty("os.name");
        System.out.println("os:" + os);
        if(os.equalsIgnoreCase("linux")){
            appDirTmp = APP_DIR_LINUX;
        }else if(os.equalsIgnoreCase("windows")){
            appDirTmp = APP_DIR_WINDOWS;
        }else{
            System.err.println("unknown os.name:" + os);
            appDirTmp = APP_DIR_WINDOWS;
        }
        
        File homeDir = new File(System.getProperty("user.home"));
        appDir = new File(homeDir, appDirTmp);
        cfgFile = new File(appDir, KGSVIEW_CONF);
        sgfDir = new File(appDir, SGF_DIR);
        
        System.out.println("app dir:" + appDir);
        System.out.println("cfg file:" + cfgFile);
        System.out.println("sgf dir:" + sgfDir);
        
        if(appDir.exists() == false){
            appDir.mkdir();
        }
        if(sgfDir.exists() == false){
            sgfDir.mkdir();
        }

    }
    
    public File getAppDir(){
        return appDir;
    }
    
    public File getCfgFile(){
        return cfgFile;
    }
    
    public File getSgfDir(){
        return sgfDir;
    }
    
    public Config getConfig(){
        return config;
    }
    
    public void start(){
        config = new Config(cfgFile);
        
        archive = new Archive();
        statisticSet = new StatisticSet();
        
        markList = new MarkList();
        markList.setConfString(config.getProperty(KgsConfigEnum.USER_LIST));
        
        filterList = new FilterList(appDir);
        
        statisticSet.resetFilter();
        statisticSet.rebuild();
        
        window = new MainWindow(markList, statisticSet);
        
        String lookAndFeelClassName = config.getProperty(KgsConfigEnum.LOOK_AND_FEEL);
        try {
            UIManager.setLookAndFeel(lookAndFeelClassName);
            SwingUtilities.updateComponentTreeUI(window);
        } catch (Exception ex) {
            System.err.println("Ui UIManager.setLookAndFeel:" + lookAndFeelClassName);
            ex.printStackTrace();
        }
        window.setLocation(50, 50);
        window.setVisible(true);
    }
    
    public void stop(){
        System.out.println("App.stop()");
        
        config.setProperty(KgsConfigEnum.USER_LIST, markList.getConfString());
        config.write();
        
        filterList.writeXml();
        
        System.exit(0);
    }
    
    public MainWindow getMainWindow(){
        return window;
    }
    
    public FilterList getFilterList(){
        return filterList;
    }
    
    public Archive getArchive(){
        return archive;
    }
    
    public void setArchive(Archive newArchive){
        archive = newArchive;
        renewStatistic();
        
        // 描画のしくみがよくわからない。
        // 下の repaint がないと、ダウンロード数がゼロのダウンロードを実行した時に
        // カレンダーが再描画されない。
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                window.repaint();
            }
        });
    }
    
    public void renewStatistic(){
        statisticSet.renew(archive);
    }
    
    public void filterSelectionChanged(){
        statisticSet.resetFilter();
        renewStatistic();
    }
    
    public void filterChanged(){
        statisticSet.rebuild();
        renewStatistic();
    }
    
    public String getCurrentUser(){
        return archive.getName();
    }
    
    // テーブルの表示に変更があるときによぶ。現状では、変更の可能性があるのは sgf ファイルのあるなし表示だけ。
    public void gameTableRepaint(){
        window.gamePanelRepaint();
    }
    
    public TimeZone getTimeZone(){
        //System.out.println("TimeZone:" + TimeZone.getDefault().toString());
        //return TimeZone.getDefault();
        //return TimeZone.getTimeZone("JST");
        return TimeZone.getTimeZone(config.getProperty(KgsConfigEnum.TIME_ZONE));
    }
    
    public void setStatusText(String text){
        System.out.println("App.setStatusText:" + text);
        window.setStatusText(text);
    }
}
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

package game.property;

import app.App;
import app.Config;
import app.KgsConfigEnum;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SgfFile {
    private enum SgfPlace {
        NET,	// サーバーにある
        DISK, 	// ローカルに取得済
        NO, 	// サーバーにすらない
        UNKNOWN	// 不明
    };
    
    private static final Pattern p = Pattern.compile("http:\\/\\/files.gokgs.com\\/games\\/(\\d{4})\\/(\\d{1,2})\\/(\\d{1,2})\\/(.*)");
    
    private String origStr;
    private File file;
    private SgfPlace place;
    
    public SgfFile(String str){
        // str は url または No
        // System.out.println("SgfFile:" + str);
        
        if(str == null){
            origStr = "";
            file = null;
            place = SgfPlace.UNKNOWN;
            return;
        }else if(str.equals("No")){
            origStr = str;
            file = null;
            place = SgfPlace.NO;
        }else{
            origStr = str;
            
            // System.out.println("SgfFile:" + str);
            
            Matcher m = p.matcher(str);
            
            if(m.matches() && m.groupCount() == 4){
                String filename = m.group(1) + "-" + m.group(2) + "-" + m.group(3) + "_" + m.group(4);
                
                // System.out.println("SgfFile:" + filename);
                file = new File(App.getInstance().getSgfDir(), filename);
                if(file.exists()){
                    place = SgfPlace.DISK;
                }else{
                    place = SgfPlace.NET;
                }
            }else{
                System.out.println("SgfFile:" + str);
                place = SgfPlace.UNKNOWN;
            }
        }
    }
    
    public void download(){
        if(place == SgfPlace.DISK || place == SgfPlace.NO || place == SgfPlace.UNKNOWN){
            return;
        }
        System.out.println("SgfFile:download");
        
        HttpURLConnection connection = null;
        URL url;
        InputStream in = null;
        OutputStream out = null;
        
        try{
            url = new URL(origStr);
        }catch(MalformedURLException e){
            System.out.println("SgfFile:MalformedURLException :" + origStr);
            return;
        }
        
        try {
            byte[] buf = new byte[4096];
            int size = 0;
            
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10 * 1000);	// 10 sec
            connection.setReadTimeout(10 * 1000);
            connection.connect();
            // showHeader(connection);
            
            in = connection.getInputStream();
            out = new FileOutputStream(file);
            
            while ((size = in.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
            out.flush();
            in.close();
            out.close();
            
            place = SgfPlace.DISK;
        } catch (SocketTimeoutException e) {
            System.out.println("SgfFile download : SocketTimeoutException:" + e);
        } catch (IOException e){
            System.out.println("SgfFile download : IOException:" + e);
        } finally{
            if(connection != null){
                connection.disconnect();
            }
        }
    }
    
    public void open(){
        Config config = App.getInstance().getConfig();
        String sgfAppPath = config.getProperty(KgsConfigEnum.SGF_APP_PATH);
                
        if(place == SgfPlace.NO || place == SgfPlace.UNKNOWN){
            return;
        }else if(place == SgfPlace.NET){
            download();
        }
        
        String command = sgfAppPath + " dummyCommand";
        System.out.println("SgfFile command :" + command);
        
        String[] cmdArray = command.split("\\s+");
        
        int size = cmdArray.length;
        cmdArray[size - 1] = file.getPath();

        for(String s : cmdArray){
            System.out.println("SgfFile open :" + s);
        }
       
        try{
            Runtime.getRuntime().exec(cmdArray);
        }catch( IOException e){
            System.out.println("SgfFile open : IOException:" + e);
        }
    }
    
    public String getOrigString(){
        return origStr;
    }
    
    @Override
    public String toString(){
        switch(place){
            case NET:
                return "net";
            case DISK:
                return "disk";
            case NO:
                return "no";
            default:
                return "unknown";
        }
    }
}
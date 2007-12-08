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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

public class HtmlLoader {
    private static final String URL_BASE = "http://www.gokgs.com/gameArchives.jsp?user=";
    private URL url;
    private String name;	// debug 用
    private int year; 	// debug 用
    private int month; 	// debug 用
    
    public HtmlLoader(String name, boolean oldAccount){
        initHtmlLoader(name, oldAccount, 0, 0);
    }
    
    public HtmlLoader(String name, boolean oldAccount, int year, int month){
        initHtmlLoader(name, oldAccount, year, month);
    }
    
    private void initHtmlLoader(String name, boolean oldAccount, int year, int month){
        this.name = name;
        this.year = year;
        this.month = month;
        
    try{
            if(year == 0 && month == 0){
                if (oldAccount == true) {
                    url = new URL(URL_BASE + name + "&oldAccounts=t");
                } else {
                    url = new URL(URL_BASE + name);
                }
            }else{
                if (oldAccount == true) {
                    url = new URL(URL_BASE + name + "&oldAccounts=t&year=" + year + "&month=" + month);
                } else {
                    url = new URL(URL_BASE + name + "&year=" + year + "&month=" + month);
                }
            }
        }catch(MalformedURLException e){
            url = null;
            System.out.println("HtmlLoader:MalformedURLException name:" + name + " year:" + year + " month:" + month);
        }
        System.out.println("HtmlLoader:url:" + url);
    }
    
    public String download(GameListDownloader glloader){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String charset = "ISO-8859-1";
        StringBuffer body = new StringBuffer();
        int contentLength = 0;
        int downloaded = 0;
        
        if(this.url == null){
            System.out.println("HtmlLoader:download : loader not initialized");
            return null;
        }
        
        try {
            int BUFSIZE = 2048;
            char[] buf = new char[BUFSIZE];
            int l;
            
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10 * 1000);	// 10 sec
            connection.setReadTimeout(10 * 1000);
            connection.connect();  // getContentLength() で暗黙的に実行される
            contentLength = connection.getContentLength();  // 不明の場合は -1
            
            // showHeader(connection);
            
            glloader.firePropertyChange("lenght", 0, contentLength);
            
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            while ((l = reader.read(buf, 0, BUFSIZE)) != -1) {
                int olddownloaded = downloaded;
                downloaded += l;
                body.append(buf, 0 ,l);
                // System.out.println("download :" +  downloaded + "/" + contentLength);

                glloader.firePropertyChange("downloded", olddownloaded, downloaded);
            }

        } catch (SocketTimeoutException e) {
            System.out.println("HtmlLoader download : SocketTimeoutException:" + e);
            body = new StringBuffer();
        } catch (IOException e){
            System.out.println("HtmlLoader download : IOException:" + e);
            body = new StringBuffer();
        } finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if(connection != null){
                connection.disconnect();
            }
        }
        
        return body.toString();
    }
    
    private void showHeader(HttpURLConnection con) {
        Map<String,List<String>> headers;
        Iterator it;
        
        headers = con.getHeaderFields();
        it = headers.keySet().iterator();
        
        System.out.println("header");
        while (it.hasNext()) {
            String key = (String) it.next();
            System.out.println(" " + key + ":" + headers.get(key));
        }
    }
    
    public String getUrlString(){
        return url.toString();
    }
}
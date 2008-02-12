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

package archive;

import game.RengoReviewGame;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.Game;
import game.RengoGame;
import game.ReviewGame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * KGS Game Archives のページ
 * 
 * <pre>
 * ウェブブラウザで見たページそのもので、指定したユーザーの指定した月の対局一覧と、
 * ユーザー登録してから今までの月の一覧を持っている。
 * 指定したユーザーが存在しなければ、対局一覧も月の一覧もない。
 * ユーザーは存在するが指定した月に対局がなければ、対局一覧はないが月の一覧はある。
 * </pre>
 */
class Page {
    private static final String URL_BASE = "http://www.gokgs.com/gameArchives.jsp?user=";
    
    private String name;
    private boolean oldAccount;
    private TimeZone timeZone;
    private int year;
    private int month;
    
    private URL url;
    
    private boolean downloaded;
    
    private ArrayList<MonthGame> monthList = null;
    private MonthGame monthGame = null;
    
    // 対局のテーブルと月のテーブルの両方があるパターン
    private static final Pattern p2 = Pattern.compile(".*?(<table.*?</table>).*?(<table.*?</table>).*");
    // 月のテーブルのみがあるパターン
    private static final Pattern p1 = Pattern.compile(".*?(<table.*?</table>).*");
    
    public Page(String name, boolean oldAccount, TimeZone timeZone){
        init(name, oldAccount, timeZone, 0, 0);
    }
    
    public Page(String name, boolean oldAccount, TimeZone timeZone, int year, int month){
        init(name, oldAccount, timeZone, year, month);
    }
    
    private void init(String name, boolean oldAccount, TimeZone zone, int year, int month){
        this.name = name;
        this.oldAccount = oldAccount;
        this.timeZone = zone;
        this.year = year;
        this.month = month;
        this.downloaded = false;
        
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
    
    public void download(Downloader glloader){
        if(downloaded || url == null){
            return;
        }
        
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String charset = "UTF-8";
        StringBuffer body = new StringBuffer();
        int contentLength = 0;
        int downloadedSize = 0;
        
        try {
            int BUFSIZE = 2048;
            char[] buf = new char[BUFSIZE];
            int l;
            
            connection = (HttpURLConnection) url.openConnection();
            if(timeZone != null){
                StringBuilder str = new StringBuilder();
                str.append("timeZone=");
                str.append(timeZone.getID());
                System.out.println("timezone:" + str.toString());
                connection.setRequestProperty("Cookie", str.toString());
            }
            connection.setConnectTimeout(10 * 1000);	// 10 sec
            connection.setReadTimeout(10 * 1000);
            connection.connect();  // getContentLength() で暗黙的に実行される
            contentLength = connection.getContentLength();  // 不明の場合は -1
            
            showHeader(connection);
            
            if(glloader != null){
                glloader.firePropertyChange("lenght", 0, contentLength);
            }
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
            while ((l = reader.read(buf, 0, BUFSIZE)) != -1) {
                int olddownloaded = downloadedSize;
                downloadedSize += l;
                body.append(buf, 0 ,l);
                // System.out.println("download :" +  downloadedSize + "/" + contentLength);
                
                if(glloader != null){
                    glloader.firePropertyChange("downloded", olddownloaded, downloadedSize);
                }
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
        
        if(body.length() == 0){
            downloaded = false;
        }else{
            downloaded = true;
            setHtml(body.toString());
        }
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
    
    public boolean isDownloaded(){
        return downloaded;
    }
    
    public boolean hasMonthList(){
        return (monthList != null) ? true : false;
    }
    
    public boolean hasGameTable(){
        return (monthGame != null) ? true : false;
    }
    
    public ArrayList<MonthGame> getMonthList(){
        return monthList;
    }
    
    public MonthGame getMonthGame(){
        return monthGame;
    }
    
    private void setHtml(String html){
        String gameTable;
        String monthTable;
        
        System.out.println("html::" + html);
                
        if(html == null || html.length() == 0){
            System.err.println("Html.download fail");
            html = null;
            return;
        }
        
        String[] line = html.split("\n");
        if(line.length != 2){
            System.out.println("error : line:" + line.length);
        }
        
        Matcher m2 = p2.matcher(line[1]);
        Matcher m1 = p1.matcher(line[1]);
        
        if(m2.matches()){
            System.out.println("month table and game table found:" + m2.group(1));
            gameTable = m2.group(1);
            monthTable = m2.group(2);
        }else if(m1.matches()){
            System.out.println("month table found:" + m1.group(1));
            gameTable = null;
            monthTable = m1.group(1);
        }else{
            System.out.println("no table found:");
            gameTable = null;
            monthTable = null;
        }
        
        if(monthTable != null){
            monthList = createMonthList(monthTable, gameTable);
        }
    }

    private static final Pattern pTD = Pattern.compile("<td.*?</td>");	// <td> 要素を抜き出す
    private static final Pattern pY = Pattern.compile("<td>([0-9]{4})</td>");
    private static final Pattern pM0 = Pattern.compile("<td><a href=\"(.*?)\">([a-zA-Z]{3})</a></td>");
    private static final Pattern pM1 = Pattern.compile("<td>([a-zA-Z]{3})</td>");
        
    private ArrayList<MonthGame> createMonthList(String monthTable, String gameTable) {
        if(monthTable == null){
            return null;
        }

        ArrayList<String> lines = lineAnalyse(monthTable);
        if(lines.size() == 0){
            return null;
        }
        
        ArrayList<MonthGame> list = new ArrayList<MonthGame>();
        
        for (String line : lines){
            Matcher mTD = pTD.matcher(line);
            
            int y = 0;
            
            while (mTD.find()) {
                String td = mTD.group();
                System.out.println("td:" + td);
                
                Matcher my = pY.matcher(td);
                Matcher m0 = pM0.matcher(td);
                Matcher m1 = pM1.matcher(td);
                
                if(my.matches()){
                    y = Integer.parseInt(my.group(1));
                    
                }else if (m0.matches()) {
                    int m = monthToInt(m0.group(2));
                    MonthGame mg = new MonthGame(name, y, m);
                    list.add(mg);
                    
                }else if(m1.matches()){
                    int m = monthToInt(m1.group(1));
                    monthGame = new MonthGame(name, y, m);
                    monthGame.setGameList(createGameList(gameTable));
                    list.add(monthGame);  
                }
            }
        }
        return list;
    }
    
    private ArrayList<Game> createGameList(String gameTable) {
        ArrayList<Game> gameList;
        ArrayList <String> list;
        boolean firstLine = true;
        Pattern p7 = Pattern.compile("<tr><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td></tr>");
        Pattern p6 = Pattern.compile("<tr><td>(.*?)</td><td colspan=\"2\">(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td></tr>");
        
        if(gameTable == null){
            return null;
        }
        gameList = new ArrayList<Game>();
        list = lineAnalyse(gameTable);
        
        for (String line : list){
            // System.out.println("html line:" + line);
            
            String sgf, setup, startTime, type, result;
            ArrayList<String> listPlayer;
            Game game;
            
            if (firstLine) {		// テーブルの一行目は White とか Black とかのコラム名なのでとばす
                firstLine = false;
                continue;
            }
            
            Matcher m7 = p7.matcher(line);
            Matcher m6 = p6.matcher(line);
            if (m7.matches()) {
                sgf = getSgfUrl(m7.group(1));
                listPlayer = parsePlayer(m7.group(2));
                listPlayer.addAll(parsePlayer(m7.group(3)));
                setup = m7.group(4);
                startTime = m7.group(5);
                type = m7.group(6);
                result = m7.group(7);
                
                if(type.equals("Rengo")){
                    String w0 = listPlayer.get(0);
                    String w1 = listPlayer.get(1);
                    String b0 = listPlayer.get(2);
                    String b1 = listPlayer.get(3);
                    
                    game = new RengoGame(sgf, b0, b1, w0, w1, setup, startTime, type, result);
                }else{
                    String w0 = listPlayer.get(0);
                    String b0 = listPlayer.get(1);
                    game = new Game(sgf, b0, w0, setup, startTime, type, result);
                }
            } else if (m6.matches()) {
                sgf = getSgfUrl(m6.group(1));
                listPlayer = parsePlayer(m6.group(2));
                setup = m6.group(3);
                startTime = m6.group(4);
                type = m6.group(5);
                result = m6.group(6);
                if(type.equals("Review")){
                    String r = listPlayer.get(0);
                    String w = listPlayer.get(1);
                    String b = listPlayer.get(2);
                    game = new ReviewGame(sgf, r, b, w, setup, startTime, type, result);
                }else if(type.equals("Rengo Review")){
                    String r = listPlayer.get(0);
                    String w = listPlayer.get(1);
                    String w1 = listPlayer.get(2);
                    String b = listPlayer.get(3);
                    String b1 = listPlayer.get(4);
                    game = new RengoReviewGame(sgf, r, b, b1, w, w1, setup, startTime, type, result);

                }else{ //DEMO
                    game = new ReviewGame(sgf, listPlayer.get(0), null, null, setup, startTime, type, result);
                }
            } else {
                System.out.println("line null:" + line);
                game = null;
            }
            
            if(game != null){
                gameList.add(game);
            }
        }
        return gameList;
    }
    
    // html のテーブルを行単位(<tr> 要素単位)の文字列に分解してリストで返す
    private ArrayList<String> lineAnalyse(String table) {
        ArrayList<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile("<tr>.*?</tr>");
        Matcher m = p.matcher(table);
        
        // System.out.println("lineAnalyse:table:" + table);
        while (m.find() == true) {
            // System.out.println("lineAnalyse:group:" + m.group());
            list.add(m.group());
        }
        
        return list;
    }
    
    private int monthToInt(String str){
        String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int m = 1;
        
        for(String ma : monthArray){
            if(ma.equals(str)){
                return m;
            }
            m += 1;
        }
        
        System.err.println("error: Html.monthToInt:" + str);
        return 0;
    }
    
    /* テーブルのユーザーのリンクをあらわす文字列から "gnugo [10k]" とかの文字列を抜き出す */
    private ArrayList<String> parsePlayer(String str){
        ArrayList<String> list = new ArrayList<String>();
        Pattern p = Pattern.compile("<a href=.*?>(.*?)</a>");
        Matcher m = p.matcher(str);
        
        //System.out.println("parsePlayer:str" + str);
        
        while (m.find()){
            list.add(m.group(1));
        }
        
        return list;
    }
    
    private String getSgfUrl(String str){
        String sgfUrl;
        Pattern p = Pattern.compile("http://.*\\.sgf");
        Matcher m = p.matcher(str);
        
        if(m.find()){
            sgfUrl = m.group();
        }else if(str.equals("No")){
            sgfUrl = "No";
        }else{
            sgfUrl = null;
        }
        
        return sgfUrl;
    }
}
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

import game.RengoReviewGame;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.Game;
import game.RengoGame;
import game.ReviewGame;

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
    private String name;
    
    private ArrayList<MonthGame> monthList = null;
    private MonthGame monthGame = null;
    
    // 対局のテーブルと月のテーブルの両方があるパターン
    private static final Pattern p2 = Pattern.compile(".*?(<table.*?</table>).*?(<table.*?</table>).*");
    // 月のテーブルのみがあるパターン
    private static final Pattern p1 = Pattern.compile(".*?(<table.*?</table>).*");
    
    public Page(String name, String html){
        this.name = name;
        
        setHtml(html);
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
            
            int year = 0;
            
            while (mTD.find()) {
                String td = mTD.group();
                System.out.println("td:" + td);
                
                Matcher y = pY.matcher(td);
                Matcher m0 = pM0.matcher(td);
                Matcher m1 = pM1.matcher(td);
                
                if(y.matches()){
                    year = Integer.parseInt(y.group(1));
                    
                }else if (m0.matches()) {
                    int month = monthToInt(m0.group(2));
                    MonthGame mg = new MonthGame(name, year, month);
                    list.add(mg);
                    
                }else if(m1.matches()){
                    int month = monthToInt(m1.group(1));
                    monthGame = new MonthGame(name, year, month);
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
        int month = 1;
        for(String m : monthArray){
            if(m.equals(str)){
                return month;
            }
            month += 1;
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
        String url;
        Pattern p = Pattern.compile("http://.*\\.sgf");
        Matcher m = p.matcher(str);
        
        if(m.find()){
            url = m.group();
        }else if(str.equals("No")){
            url = "No";
        }else{
            url = null;
        }
        
        return url;
    }
}
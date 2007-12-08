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

import game.RengoReviewGame;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import game.Game;
import game.RengoGame;
import game.ReviewGame;

public class Html {
    private String name;
    private boolean oldAccount;
    private int year;       // 指定がなければゼロ
    private int month;      // 指定がなければゼロ
    
    private HtmlLoader loader;
    
    private String html;
    private String gameTable;
    private String monthTable;
    
    // 対局のテーブルと月のテーブルの両方があるパターン
    private static final Pattern p2 = Pattern.compile(".*?(<table.*?</table>).*?(<table.*?</table>).*");
    // 月のテーブルのみがあるパターン
    private static final Pattern p1 = Pattern.compile(".*?(<table.*?</table>).*");
    
    // 名前だけで検索した時のパターン
    public Html(String name, boolean oldAccount){
        this.name = name;
        this.oldAccount = oldAccount;
        this.year = 0;
        this.month = 0;
        
        this.loader = new HtmlLoader(name, oldAccount);
        
        html = null;
        gameTable = null;
        monthTable = null;
    }

    /*
    html の月のテーブルから抜き出した文字列から作るパターン
    gameArchives.jsp?user=**name**&amp;oldAccounts=t&amp;year=2007&amp;month=6
    gameArchives.jsp?user=**name**&amp;year=2007&amp;month=6
     */
    private static final Pattern pName = Pattern.compile("gameArchives.jsp\\?user=(.*)");
    private static final Pattern pYear = Pattern.compile(";year\\=(\\d+)");
    private static final Pattern pMonth = Pattern.compile(";month\\=(\\d+)");
    
    public Html(String str){
        String[] a = str.split("\\&amp");
        
        name = null;
        year = 0;
        month = 0;
        
        String nameTmp = "";
        String yearTmp = "";
        String monthTmp = "";
        
        if(a.length == 4){
            nameTmp = a[0];
            yearTmp = a[2];
            monthTmp = a[3];

            oldAccount = true;
        }else if(a.length == 3){
            nameTmp = a[0];
            yearTmp = a[1];
            monthTmp = a[2];
                       
            oldAccount = false;
        }else{
            System.err.println("Html():" + str);
        }
        
        Matcher mName = pName.matcher(nameTmp);
        Matcher mYear = pYear.matcher(yearTmp);
        Matcher mMonth = pMonth.matcher(monthTmp);
        
        if(mName.matches()){
            name = mName.group(1);
        }else{
            System.err.println("Html():name:" + str);
        }
        
        oldAccount = true;
        
        if(mYear.matches()){
            year = Integer.parseInt(mYear.group(1));
        }else{
            System.err.println("Html():Year:" + str);
        }
        
        if(mMonth.matches()){
            month = Integer.parseInt(mMonth.group(1));
        }else{
            System.err.println("Html():Month:" + str);
        }
        
        System.out.println("Html():" + name + ":" + year + ":" + month + ":");
        this.loader = new HtmlLoader(name, oldAccount, year, month);
        
        html = null;
        gameTable = null;
        monthTable = null;
    }
    
    public void download(GameListDownloader glloader){
        html = loader.download(glloader);
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
    }
    
    public boolean hasMonthTable(){
        return (monthTable != null) ? true : false;
    }
    
    public boolean hasGameTable(){
        return (gameTable != null) ? true : false;
    }
    
    public ArrayList<Game> getGameList() {
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
    
    // ここから返す Html に直近月は含まれない
    public ArrayList<Html> getMonthList() {
        ArrayList<Html> monthList = new ArrayList<Html>();
        ArrayList<String> lines;
        Pattern pTD = Pattern.compile("<td.*?</td>");	// <td> 要素を抜き出す
        Pattern pM = Pattern.compile("<td><a href=\"(.*?)\">([a-zA-Z]{3})</a></td>"); // <td> 要素から月をあらわす文字列を抜き出す
        
        if(monthTable == null){
            return monthList; // 空のリスト
        }
        
        lines = lineAnalyse(monthTable);
        
        for (String line : lines){
            Matcher mTD = pTD.matcher(line);
            
            while (mTD.find()) {
                String td = mTD.group();
                
                Matcher m = pM.matcher(td);
                if (m.matches()) {
                    //System.out.println("Html.getMonthList:month:" + m.group(2) + ":" + m.group(1));
                    
                    Html h = new Html(m.group(1));
                    monthList.add(h);
                }
            }
        }
        return monthList;
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
    
    public int compare(int year, int month){
        if(this.year < year){
            return -1;
        }else if(this.year == year){
            if (this.month < month){
                return -1;
            }else if(this.month == month){
                return 0;
            }else{
                return 1;
            }
        }else{
            return 1;
        }
    }
    
    @Override
    public String toString(){
        return loader.getUrlString();
    }
}
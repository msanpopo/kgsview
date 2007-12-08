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

package ui;

import app.App;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import app.Resource;

import java.awt.geom.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import game.Game;
import game.GameTableModel;
import game.GameType;
import game.UserResult;
/*
 * ランクと勝率の推移を表示するためのグラフ
 */
@SuppressWarnings("serial")
public class Graph extends JPanel implements TableModelListener{
    private GameTableModel model;
    
    public Graph(GameTableModel model){
        this.model = model;
        
        model.addTableModelListener(this);
        setBackground(Color.white);
    }
    
    @Override
    public void tableChanged(TableModelEvent e) {
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Calendar first = new GregorianCalendar(App.getInstance().getTimeZone());	// ゲームのある最初の月の一日
        Calendar last = new GregorianCalendar(App.getInstance().getTimeZone());	// ゲームのある最後の月の翌月の一日
        ArrayList<Calendar> calendarArray;	// 横軸の月の線描画用
        
        if(model.getRowCount() == 0){
            return;
        }
        
        first.setTime(model.first().getDate());
        first.set(Calendar.DAY_OF_MONTH, 1);
        first.set(Calendar.HOUR_OF_DAY, 0);
        first.set(Calendar.MINUTE, 0);
        first.set(Calendar.SECOND, 0);
        first.set(Calendar.MILLISECOND, 0);
        
        last.setTime(model.last().getDate());
        last.set(Calendar.DAY_OF_MONTH, 1);
        last.set(Calendar.HOUR_OF_DAY, 0);
        last.set(Calendar.MINUTE, 0);
        last.set(Calendar.SECOND, 0);
        last.set(Calendar.MILLISECOND, 0);
        
        last.add(Calendar.MONTH, 1);

        calendarArray = calcSpan(first, last);

        Graphics2D g2 = (Graphics2D)g;
        int wDisplay = getWidth();		// 表示部分全体のサイズ
        int hDisplay = getHeight();
        BasicStroke line1 = new BasicStroke(1.0F);
        BasicStroke line2 = new BasicStroke(2.0F);
        
        double xBase = 50;				// グラフ周辺の余白サイズ（文字表示部分のぞく）
        double yBase = 50;
        double w = wDisplay - xBase * 2.0;	// グラフのサイズ
        double h = hDisplay - yBase * 2.0;
        double gx0 = xBase;			// グラフ左下（原点）の座標
        double gy0 = yBase + h;
        double gx1 = xBase + w;		// グラフ右上の座標
        double gy1 = yBase;
        
        long span = last.getTime().getTime() - first.getTime().getTime();
        
        g2.setPaint(Color.BLACK);
        g2.setStroke(line2);
        g2.draw(new Rectangle2D.Double(xBase, yBase, w, h)); // 外枠
        for(int i = 1; i < 4; ++i){
            double y = gy0 - h * i / 4.0;
            g2.draw(new Line2D.Double(gx0, y, gx0 + w, y)); // 太い横線
        }
        
        g2.setStroke(line1);
        // 縦線描画
        for(Calendar c : calendarArray){
            int calN = calendarArray.size();
            int a;	// 何ヶ月毎に線を引くか
            
            if(calN > 48){  	// ４年以上のデータなら半年毎
                a = 6;
            }else if(calN > 24){	// ２年以上なら３ヶ月毎
                a = 3;
            }else if(calN > 12){	// １年以上なら２ヶ月毎
                a = 2;
            }else{
                a = 1;
            }
            
            if((c.get(Calendar.MONTH) + 1) % a == 0){
                long s = c.getTime().getTime() - first.getTime().getTime();
                double x = gx0 + w * s / span;
                
                g2.draw(new Line2D.Double(x, gy0, x, gy1));
                
                SimpleDateFormat sdf = new SimpleDateFormat("yy/MM");
                String str = sdf.format(c.getTime());
                
                g2.drawString(str, (int)x - 20, (int)gy0 + 20);
            }
        }
        
        g2.setColor(Color.RED);
        g2.drawString(Resource.get("winRatio"), (int)gx1 + 10, (int)gy1 - 20);
        String[] y1String = {"0.00", "0.25", "0.50", "0.75", "1.00"};
        for(int i = 0; i < y1String.length; ++i){
            double y = gy0 - h * i / 4.0;
            g2.drawString(y1String[i], (int)gx1 + 10, (int)y + 5);
        }
        
        g2.setColor(Color.BLUE);
        g2.drawString(Resource.get("rank"), (int)gx0 - 35, (int)gy1 - 20);
        
        // 横線
        int min = 1;
        int max = 41;
        String[] rankString = {"30k", "25k", "20k", "15k", "10k", " 5k", " 1k", " 1d", " 5d", " 9d"};
        int count = 0;
        int dmaxmin = max - min;

        for(int i = min; i < max + 1; ++i){
            double y = h * (i - min) / dmaxmin;
            
            if(i == 1 || i == 6 || i == 11 || i == 16 || i == 21 || i == 26 || i == 30 || i == 31 || i == 35 || i == 39){
                g2.setColor(Color.BLACK);
                g2.draw(new Line2D.Double(gx0, gy0 - y, gx1, gy0 - y));
                g2.setColor(Color.BLUE);
                g2.drawString(rankString[count], (int)gx0 - 30, (int)(gy0 - y) + 5);
                count += 1;
            }
        }
        
        double x0 = -1.0;
        double y0 = -1.0;
        double yr0 = -1.0;
        
        ArrayList<UserResult> result100;
        
        result100 = new ArrayList<UserResult>();
        
        int n = model.getRowCount();
        for(int i = 0; i < n; ++i){
            Game game = model.getGame(i);
            
            if(game.getGameType() != GameType.RANKED){
                continue;
            }
            
            Date d = game.getDate();
            int r =  game.getUserRank().getRankNumber();
            UserResult result = game.getUserResult();
            
            if(result100.size() == 100){
                result100.remove(0);
            }
            result100.add(result);
            
            long s = d.getTime() - first.getTime().getTime();
            double x1 = w * s / span;
            
            // 勝率
            // System.out.println("Graph:paint p" + p);
            double p = winPercentage(result100);
            double y1 = h * p;
            g2.setColor(Color.RED);
            if(y0 > 0.0 && y1 > 0.0){
                g2.setStroke(line2);
                g2.draw(new Line2D.Double(gx0 + x0, gy0 - y0 , gx0 + x1, gy0 - y1));
            }
            
            // ランク
            double yr1 = h * (r - min) / (max - min);
            g2.setColor(Color.BLUE);
            if(yr0 > 0.0 && yr1 > 0.0){
                g2.setStroke(line2);
                g2.draw(new Line2D.Double(gx0 + x0, gy0 - yr0 , gx0 + x1, gy0 - yr1));
            }
            
            x0 = x1;
            y0 = y1;
            yr0 = yr1;
        }
    }
    
    private static double winPercentage(ArrayList<UserResult> result){
        int winN = 0;
        int allN = 0;
        
        for(UserResult ur : result){
            allN += 1;
            if(ur == UserResult.WIN){
                winN += 1;
            }
        }
        
        return (double)winN / allN;
    }
    
    private ArrayList<Calendar> calcSpan(Calendar first, Calendar last){
        ArrayList<Calendar> calendarArray = new ArrayList<Calendar>();
        
        calendarArray.add(first);
        
        Calendar c = (Calendar)first.clone();
        c.add(Calendar.MONTH, 1);
        
        while(!c.equals(last)){
            System.out.println("Graph:  x:" + c.getTime());
            calendarArray.add(c);
            
            Calendar next = (Calendar)c.clone();
            next.add(Calendar.MONTH, 1);
            c = next;
        }
        calendarArray.add(last);
        
        return calendarArray;
    }
}
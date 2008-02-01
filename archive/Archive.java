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

import app.App;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimeZone;

public class Archive {
    private static final String MONTH_LIST = "month.txt";
    
    private String name = "";
    private ArrayList<MonthGame> monthList = new ArrayList<MonthGame>();
    
    public Archive(){
    }
        
    public Archive(String name){
        this.name = name;
    }
    
    public void write(){
        if(name == null || name.isEmpty() || monthList.isEmpty()){
            return;
        }
        
        File appDir = App.getInstance().getAppDir();
        File topDir = new File(appDir, name);
        File listFile = new File(topDir, MONTH_LIST);
        
        if(topDir.exists() == false){
            topDir.mkdir();
        }

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(listFile));
            
            for (MonthGame mg : monthList) {
                int year = mg.getYear();
                int month = mg.getMonth();
                StringBuilder str = new StringBuilder();
                str.append(year);
                if(month < 10){
                    str.append("-0").append(month);
                }else{
                    str.append("-").append(month);
                }
                bw.write(str.toString());
                bw.newLine();

                mg.write(topDir);
            }
        } catch (IOException ex) {
            System.out.println("MonthGame write :: error:" + ex);
        } finally {
            if (bw != null) {
                try {
                    bw.flush();
                    bw.close();
                } catch (IOException e) {
                    System.out.println("MonthGame flush  close :: error:" + e);
                }
            }
        }
    }
    
    public void read(){
        if(name == null || name.isEmpty()){
            return;
        }
        
        File appDir = App.getInstance().getAppDir();
        File topDir = new File(appDir, name);
        File listFile = new File(topDir, MONTH_LIST);
        
        if(listFile.exists() == false){
            return;
        }
        
        BufferedReader br = null;
        try{
            String line;
            
            br = new BufferedReader(new FileReader(listFile));
            
            while((line = br.readLine()) != null){
                String[] strArray = line.split("-");
                int year = Integer.parseInt(strArray[0]);
                int month = Integer.parseInt(strArray[1]);
                
                MonthGame monthGame = new MonthGame(name, year, month);
                monthGame.read(topDir);
            }
            
        }catch(IOException ex){
            System.out.println("Archive read :: err:" + name + " :: " + ex);
            
        }finally{
            if(br != null){
                try{
                    br.close();
                }catch(IOException ex){
                    System.err.println("error:Archive:br.close:" + ex);
                }
            }
        }
        
        return;
    }
    
    public void update(){
        PageLoader loader = new PageLoader(name, false, TimeZone.getDefault());
        Page page = loader.download(null);
        
        if(page.hasMonthList()){
            
        }
    }
}
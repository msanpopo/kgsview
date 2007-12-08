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

package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

@SuppressWarnings("serial")
public class Config{
    private Properties prop;
    private File cfgFile;
    
    public Config(File cfgFile){
        this.prop = new Properties();
        this.cfgFile = cfgFile;
        
        InputStream is = null;
        
        try {
            is = new FileInputStream(cfgFile);
            
            try{
                prop.load(is);
            }catch(Exception e){
                System.out.println("Config prop.load:error e:" + e);
            }
            
        }catch(FileNotFoundException e){
            System.err.println(this.getClass() + ":" + e);
            // デフォルト値のセット
            for(KgsConfig kc : KgsConfig.values()){
                setProperty(kc, kc.getDefaultValue());
            }
        }finally{
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Config prop.read close error e:" + e);
                }
            }
        }
    }
    
    public void setProperty(KgsConfig kgsConfig, String value){
        if(value == null){
            System.err.println("error:Config.setProperty: value is null");
            return;
        }
        prop.setProperty(kgsConfig.getKey(), value);
    }
    
    public String getProperty(KgsConfig kgsConfig){
        String value =  prop.getProperty(kgsConfig.getKey());
        
        // System.out.println("getProperty : name:" + propertyName + " value:" + value);
        
        if(value == null){
            value = kgsConfig.getDefaultValue();
            prop.setProperty(kgsConfig.getKey(), kgsConfig.getDefaultValue());
        }
        return value;
    }
    
    public void setBooleanProperty(KgsConfig kgsConfig, boolean value){
        prop.setProperty(kgsConfig.getKey(), Boolean.toString(value));
    }
    
    public boolean getBooleanProperty(KgsConfig kgsConfig){
        String value =  prop.getProperty(kgsConfig.getKey());
        
        // System.out.println("getProperty : name:" + propertyName + " value:" + value);
        
        if(value == null){
            value = kgsConfig.getDefaultValue();
            prop.setProperty(kgsConfig.getKey(), kgsConfig.getDefaultValue());
        }
        return Boolean.valueOf(value);
    }
    
    public void setIntProperty(KgsConfig kgsConfig, int value){
        prop.setProperty(kgsConfig.getKey(), Integer.toString(value));
    }
    
    public int getIntProperty(KgsConfig kgsConfig){
        String value =  prop.getProperty(kgsConfig.getKey());
        
        //System.out.println("getProperty : name:" + kgsConfig.getKey() + " value:" + value);
        
        if(value == null){
            value = kgsConfig.getDefaultValue();
            prop.setProperty(kgsConfig.getKey(), kgsConfig.getDefaultValue());
        }
        return Integer.parseInt(value);
    }
    
    public void write(){
        OutputStream os = null;
        
        try{
            os = new FileOutputStream(cfgFile);
            prop.store(os, null);
        }catch(FileNotFoundException e){
            System.err.println(this.getClass() + ":" + e);
        }catch (IOException e){
            System.err.println(this.getClass() + ":" + e);
        }finally{
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.println("Config prop.write close error e:" + e);
                }
            }
        }
    }
}
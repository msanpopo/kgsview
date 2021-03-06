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

import java.util.ResourceBundle;

public class Resource{
    private static Resource resource;
    private ResourceBundle bundle;
    
    public static Resource getInstance(){
        if(resource == null){
            resource = new Resource();
        }
        return resource;
    }
    private Resource(){
        bundle = ResourceBundle.getBundle("app.resource.Resource");
    }
        
    public static String get(String key){
        return ResourceBundle.getBundle("app.resource.Resource").getString(key);
    }
}
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

package action;


import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import app.App;
import app.Resource;
import archive.Archive;


public class UpdateAction  extends AbstractAction{
            
    public UpdateAction(){
        ClassLoader cl = this.getClass().getClassLoader();
        Icon icon  = new ImageIcon(cl.getResource("icon/stock_refresh.png"));
        
        putValue(Action.NAME, Resource.get("update"));
        putValue(Action.SMALL_ICON, icon);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("UpdateAction:performed:" + e);
        
        App app = App.getInstance();
        
        Archive archive = app.getArchive();
        archive.update();

    }
}
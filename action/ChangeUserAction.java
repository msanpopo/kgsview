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

package action;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

import app.App;
import archive.Archive;

public class ChangeUserAction  extends AbstractAction{
    private String newUser;
    
    public ChangeUserAction(String newUser){
        this.newUser = newUser;
        
        putValue(Action.NAME, newUser);
        putValue(Action.ACTION_COMMAND_KEY, newUser);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        doAction();
    }
    
    public void doAction(){
        App app = App.getInstance();
        
        if(newUser == null || newUser.equals("") || newUser.equals(app.getCurrentUser())){
            return;
        }
        
        System.out.println("ChangeUserAction:performed:cmd:" + newUser);

        Archive newArchive = new Archive(newUser);
        
        newArchive.read();
        
        if(newArchive.getSize() == 0){
            newArchive.update();
        }
        
        app.setArchive(newArchive);
    }
}
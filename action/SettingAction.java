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

package action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import app.Resource;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import ui.FilterSelectPanel;
import ui.SettingPanel;

@SuppressWarnings("serial")
public class SettingAction  extends AbstractAction{
    
    public SettingAction(){
        putValue(Action.NAME, Resource.get("option"));
        putValue(Action.ACTION_COMMAND_KEY, "Option");
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Setting:performed:" + e);
        
        doAction();
    }
    
    public void doAction(){
        SettingPanel settingPanel = new SettingPanel();
        FilterSelectPanel filterSelectPanel = new FilterSelectPanel();
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(Resource.get("global"), settingPanel);
        tabbedPane.addTab(Resource.get("selectFilter"), filterSelectPanel);
        
        String[] options = {Resource.get("close")};
        int retval = JOptionPane.showOptionDialog(null, tabbedPane, "Setting",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);
        
        System.out.println("SettingAction: retval:" + retval);
    }
}
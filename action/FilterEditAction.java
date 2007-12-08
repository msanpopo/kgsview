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

import app.Resource;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ui.FilterListPanel;

public class FilterEditAction extends AbstractAction{
    public FilterEditAction() {
        putValue(Action.NAME, Resource.get("editFilter"));
        putValue(Action.ACTION_COMMAND_KEY, "FilterEdit");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        doAction();
    }
    
    public void doAction(){
        JPanel panel = new FilterListPanel();
        
        String[] options = {Resource.get("close")};
        int retval = JOptionPane.showOptionDialog(null, panel, "Filter",
                                   JOptionPane.DEFAULT_OPTION,
                                   JOptionPane.PLAIN_MESSAGE,
                                   null, options, options[0]);
        
        System.out.println("FilterEditAction: retval:" + retval);
    }
}
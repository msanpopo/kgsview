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

import app.App;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import app.Resource;
import game.property.SgfFile;

@SuppressWarnings("serial")
public class SgfOpenAction  extends AbstractAction{
    private SgfFile sgfFile;
    
    public SgfOpenAction(SgfFile sgfFile){
        this.sgfFile = sgfFile;

        putValue(Action.NAME, Resource.get("sgfOpen"));
        putValue(Action.ACTION_COMMAND_KEY, "SgfOpen");
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("SgfOpen:performed:" + e);
        
        sgfFile.open();
        
        App.getInstance().gameTableRepaint(); // ディスク上になくてダウンロードすると表示がかわるから
    }
}
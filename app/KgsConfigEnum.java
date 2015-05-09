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

public enum KgsConfigEnum {
    USER_LIST("userList", ""),
    LOOK_AND_FEEL("lookAndFeel", "javax.swing.plaf.metal.MetalLookAndFeel"),
    OLD_ACCOUNT("oldAccount", "false"),
    SGF_APP_PATH("sgfAppPath", "javaws cgoban.jnlp -open"),
    WINDOW_WIDTH("windowWidth", "800"),
    WINDOW_HEIGHT("windowHeight", "700"),
    FILTER_GAME_LIST("filterGameList", ""),
    FILTER_TYPE("filterType", ""),
    FILTER_SETUP("filterSetup", "Default(R F S T)"),
    FILTER_RECORD("filterRecord", "Ranked"),
    FILTER_MONTH("filterMonth", "Default(R F S T)"),
    FILTER_OPPONENT("filterOpponent", "Default(R F S T)"),
    TIME_ZONE("timeZone", "JST");
    
    private final String key;
    private final String defaultValue;

    private KgsConfigEnum(String key, String defaultValue){
        this.key = key;
        this.defaultValue = defaultValue;
    }
    
    public String getKey(){
        return key;
    }
    
    public String getDefaultValue(){
        return defaultValue;
    }
}
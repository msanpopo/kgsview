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

package ui;

import archive.Downloader;
import app.App;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import app.Resource;

public class DownloadDialog extends JDialog implements ActionListener, PropertyChangeListener{
    private JOptionPane optionPane;
    private JButton cancelButton;
    private JLabel infoLabel;
    private JProgressBar progressBar;
    
    private Downloader loader;
    
    public DownloadDialog(Downloader loader){
        super(App.getInstance().getMainWindow(), Resource.get("downloading"), true);

        this.loader = loader;
        
        infoLabel = new JLabel();
        
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setMinimum(0);
        progressBar.setValue(0);
        progressBar.setIndeterminate(false);
        
        JPanel message = new JPanel();
        message.setLayout(new BoxLayout(message, BoxLayout.Y_AXIS));
        message.add(infoLabel);
        message.add(new JLabel(" "));
        message.add(progressBar);
        
        cancelButton = new JButton(Resource.get("cancel"));
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(this);
        
        Object[] options = {cancelButton};
        
        optionPane = new JOptionPane(message,
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION,
                null, options, options[0]);
        
        this.setContentPane(this.optionPane);
        this.pack();
        this.setLocationByPlatform(true);
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.out.println("close window.");
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        loader.cancel();
    }
        
    public void setInfoText(String text){
        infoLabel.setText(text);
        pack();
    }
    
    public void resetProgressBar(int maximum){
        progressBar.setValue(0);
        progressBar.setMinimum(0);
        if(maximum == -1){
            progressBar.setMaximum(0);
            progressBar.setIndeterminate(true);
        }else{
            progressBar.setMaximum(maximum);
            progressBar.setIndeterminate(false);
        }
        progressBar.setString("");
    }
    
    public void setProgressValue(int n){
        int max = progressBar.getMaximum();
        
        if(max == 0){
            String str = Integer.toString(n);
            
            progressBar.setString(str);
        }else{
            String str = n + "/" + max;
            
            // System.out.println("DownloadDialog:" + str);
            
            progressBar.setValue(n);
            progressBar.setString(str);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propName = evt.getPropertyName();
//        System.out.println("DownloadDialog.propertyChange:" + propName + " value:" + evt.getNewValue());
        
        if ("lenght".equals(propName)) {
            int lenght = (Integer)evt.getNewValue();
            resetProgressBar(lenght);
        }else if("downloded".equals(propName)){
            int n = (Integer)evt.getNewValue();
            setProgressValue(n);
        }
    }
}
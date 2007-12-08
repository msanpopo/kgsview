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

package ui;

import app.App;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class KgsGraphPanel extends javax.swing.JPanel implements TableModelListener{
    private static final String URL_BASE = "http://www.gokgs.com/servlet/graph/";
    //private static final String URL_EXT = "-ja_JP.png";
    private static final String URL_EXT = "-en_US.png";
    
    private BufferedImage image;
    private JPanel graphPanel;
    
    public KgsGraphPanel() {
        image = null;
        
        initComponents();
        
        graphPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponents(g);
                
                //windows でユーザーの変更時にグラフがクリアされない。linux だとクリアされる。なぜ？
                // linux でも コンボのプルダウンメニューからならクリアされるのに、手入力してエンターだとクリアされない。(2007/9/15)
                Graphics2D g2 = (Graphics2D)g;
                System.out.println("image:" + image);
                
                if(image == null){
                    return;
                }
                int w = image.getWidth();
                int h = image.getHeight();
                
                int w_panel = graphPanel.getWidth();
                int h_panel = graphPanel.getHeight();
                
                int x = (w_panel - w) / 2;
                int y = (h_panel - h) / 2;
                g2.drawImage(image, null, x, y);
            }
        };
        
        graphPanelBase.setLayout(new BorderLayout());
        graphPanelBase.add(graphPanel, BorderLayout.CENTER);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        image = null;
        graphPanel.repaint();
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        downloadButton = new javax.swing.JButton();
        graphPanelBase = new javax.swing.JPanel();

        setBackground(new java.awt.Color(221, 221, 136));
        downloadButton.setText(java.util.ResourceBundle.getBundle("app/resource/Resource").getString("download"));
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        graphPanelBase.setBackground(new java.awt.Color(221, 221, 136));
        javax.swing.GroupLayout graphPanelBaseLayout = new javax.swing.GroupLayout(graphPanelBase);
        graphPanelBase.setLayout(graphPanelBaseLayout);
        graphPanelBaseLayout.setHorizontalGroup(
            graphPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );
        graphPanelBaseLayout.setVerticalGroup(
            graphPanelBaseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(graphPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(downloadButton))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(downloadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(graphPanelBase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        if(image != null){
            return;
        }
        String name = App.getInstance().getCurrentUser();
        URL url;

        if(name == null || name.equals("")){
            return;
        }
        
        try {
            url = new URL(URL_BASE + name + URL_EXT);
            System.out.println("url:" + url);
        } catch (MalformedURLException ex) {
            System.err.println("KgsGraphPanel:URL()" + ex);
            ex.printStackTrace();
            return;
        }
        
        try {
            image = ImageIO.read(url);
        } catch (IOException ex) {
            System.err.println("KgsGraphPanel:" + url);
            return;
        }
        graphPanel.repaint();
    }//GEN-LAST:event_downloadButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton downloadButton;
    private javax.swing.JPanel graphPanelBase;
    // End of variables declaration//GEN-END:variables
}
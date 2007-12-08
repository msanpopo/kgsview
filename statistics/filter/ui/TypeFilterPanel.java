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

package statistics.filter.ui;

import game.GameType;
import statistics.filter.TypeFilter;

public class TypeFilterPanel extends javax.swing.JPanel {
    private TypeFilter filter;
    
    public TypeFilterPanel() {
        filter = new TypeFilter();
        
        initComponents();
        
        setTypeFilter(filter);
    }
    
    public void setTypeFilter(TypeFilter filter){
        this.filter = filter;
        
        rankedCheckBox.setSelected(filter.isSelected(GameType.RANKED));
        freeCheckBox.setSelected(filter.isSelected(GameType.FREE));
        teachingCheckBox.setSelected(filter.isSelected(GameType.TEACHING));
        demoCheckBox.setSelected(filter.isSelected(GameType.DEMO));
        reviewCheckBox.setSelected(filter.isSelected(GameType.REVIEW));
        simulCheckBox.setSelected(filter.isSelected(GameType.SIMUL));
        tournamentCheckBox.setSelected(filter.isSelected(GameType.TOURNAMENT));
        rengoCheckBox.setSelected(filter.isSelected(GameType.RENGO));
        rengoReviewCheckBox.setSelected(filter.isSelected(GameType.RENGO_REVIEW));
    }
    
    public TypeFilter getTypeFilter(){
        filter.setSelected(GameType.RANKED, rankedCheckBox.isSelected());
        filter.setSelected(GameType.FREE, freeCheckBox.isSelected());
        filter.setSelected(GameType.TEACHING, teachingCheckBox.isSelected());
        filter.setSelected(GameType.DEMO, demoCheckBox.isSelected());
        filter.setSelected(GameType.REVIEW, reviewCheckBox.isSelected());
        filter.setSelected(GameType.SIMUL, simulCheckBox.isSelected());
        filter.setSelected(GameType.TOURNAMENT, tournamentCheckBox.isSelected());
        filter.setSelected(GameType.RENGO, rengoCheckBox.isSelected());
        filter.setSelected(GameType.RENGO_REVIEW, rengoReviewCheckBox.isSelected());
        
        return filter;
    }

    // <editor-fold defaultstate="collapsed" desc=" Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        rankedCheckBox = new javax.swing.JCheckBox();
        freeCheckBox = new javax.swing.JCheckBox();
        teachingCheckBox = new javax.swing.JCheckBox();
        demoCheckBox = new javax.swing.JCheckBox();
        reviewCheckBox = new javax.swing.JCheckBox();
        simulCheckBox = new javax.swing.JCheckBox();
        tournamentCheckBox = new javax.swing.JCheckBox();
        rengoCheckBox = new javax.swing.JCheckBox();
        rengoReviewCheckBox = new javax.swing.JCheckBox();

        rankedCheckBox.setText("Ranked");
        rankedCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rankedCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        freeCheckBox.setText("Free");
        freeCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        freeCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        teachingCheckBox.setText("Teaching");
        teachingCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        teachingCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        demoCheckBox.setText("Demo");
        demoCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        demoCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        reviewCheckBox.setText("Review");
        reviewCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        reviewCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        simulCheckBox.setText("Simul");
        simulCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        simulCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        tournamentCheckBox.setText("Tournament");
        tournamentCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        tournamentCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        rengoCheckBox.setText("Rengo");
        rengoCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rengoCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        rengoReviewCheckBox.setText("Rengo Review");
        rengoReviewCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        rengoReviewCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rankedCheckBox)
                    .addComponent(freeCheckBox)
                    .addComponent(teachingCheckBox)
                    .addComponent(demoCheckBox)
                    .addComponent(reviewCheckBox)
                    .addComponent(simulCheckBox)
                    .addComponent(tournamentCheckBox)
                    .addComponent(rengoCheckBox)
                    .addComponent(rengoReviewCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rankedCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(freeCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(teachingCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(demoCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reviewCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(simulCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tournamentCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rengoCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rengoReviewCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox demoCheckBox;
    private javax.swing.JCheckBox freeCheckBox;
    private javax.swing.JCheckBox rankedCheckBox;
    private javax.swing.JCheckBox rengoCheckBox;
    private javax.swing.JCheckBox rengoReviewCheckBox;
    private javax.swing.JCheckBox reviewCheckBox;
    private javax.swing.JCheckBox simulCheckBox;
    private javax.swing.JCheckBox teachingCheckBox;
    private javax.swing.JCheckBox tournamentCheckBox;
    // End of variables declaration//GEN-END:variables
}
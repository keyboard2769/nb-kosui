/*
 * Copyright (C) 2019 Key Parker from K.I.C
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package kosui.pppswingui;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import kosui.ppplogic.ZcRangedModel;
import processing.core.PApplet;

/**
 * .<br>
 * .<br>
 */
public class ScGlassWindow extends JWindow{
  
  public static final Color C_DEFAULT_PANE_COLOR = Color.BLACK;
  public static final Color C_DEFAULT_THEME_COLOR = Color.GRAY;
  public static final int C_DEFAULT_BORDER_WIDTH = 2;
  
  private final JWindow self;
  private int cmAnchorX=0;
  private int cmAnchorY=0;
  private float cmNormalOpacity = 0.4f;
  private float cmEngagedOpacity = 0.7f;
  private int cmEngagedCount = 0;
  private int cmEngagedMax = 16;
  
  private final int cmWidth;
  private final int cmHeight;
  
  public final ScCanvas cmCanvas;
  public final JPopupMenu cmPopup;
  
  private final
  MouseAdapter cmMouseAdapter=new MouseAdapter() {
    @Override public void mouseReleased(MouseEvent e) {
      if(e.isPopupTrigger()){
        cmPopup.show(e.getComponent(), e.getX(), e.getY());
      }else{        
        cmAnchorX=0;
        cmAnchorY=0;
      }//..?
    }//+++
  };//***
  
  private final 
  MouseMotionAdapter cmMouseMotionAdapter = new MouseMotionAdapter() {
    @Override public void mouseDragged(MouseEvent e) {
      self.setOpacity(cmEngagedOpacity);
      cmEngagedCount=cmEngagedMax;
      if(cmAnchorX==0 && cmAnchorY==0){
        cmAnchorX=e.getXOnScreen()-self.getLocationOnScreen().x;
        cmAnchorY=e.getYOnScreen()-self.getLocationOnScreen().y;
      }//..?
      self.setLocation(
        e.getXOnScreen()-cmAnchorX, 
        e.getYOnScreen()-cmAnchorY
      );
    }//+++
  };//***
  
  private final SiPaintable cmDefaulBackground = new SiPaintable() {
    @Override public void ccPaint(Graphics pxGI) {
      pxGI.setColor(C_DEFAULT_PANE_COLOR);
      pxGI.fillRect(
        C_DEFAULT_BORDER_WIDTH, C_DEFAULT_BORDER_WIDTH,
        cmWidth-C_DEFAULT_BORDER_WIDTH*2, cmHeight-C_DEFAULT_BORDER_WIDTH*2
      );
    }//+++
  };//***
  
  public ScGlassWindow(Frame pxOwner, int pxW, int pxH) {
    
    super(pxOwner);
    self=this;
    
    //-- popup
    cmPopup=new JPopupMenu();
    
    //-- canvas
    cmWidth=ZcRangedModel.ccLimitInclude(pxW, 100, 2047);
    cmHeight=ZcRangedModel.ccLimitInclude(pxH, 100, 2047);
    cmCanvas=new ScCanvas(cmWidth, cmHeight);
    cmCanvas.addMouseListener(cmMouseAdapter);
    cmCanvas.addMouseMotionListener(cmMouseMotionAdapter);
    cmCanvas.ccAddPaintObject(cmDefaulBackground);
    
    //-- pack
    self.getContentPane().add(cmCanvas);
    
  }//..!
  
  public final void ccFinish(){
    ccFinish(100, 100, true);
  }//..!
  
  public final void ccFinish(boolean pxVisible){
    ccFinish(100, 100, pxVisible);
  }//..!
  
  public final void ccFinish(int pxX, int pxY, boolean pxVisible){
    self.pack();
    self.setOpacity(cmNormalOpacity);
    self.setAlwaysOnTop(true);
    self.setVisible(pxVisible);
    if(pxX>0 && pxY>0){
      self.setLocation(pxY, pxY);
    }//..?
  }//..!
  
  public final void ccSetNormalOpacity(float pxZeroToOne){
    cmNormalOpacity=PApplet.constrain(pxZeroToOne, 0.0f, 1.0f);
  }//+++
  
  public final void ccSetEngagedOpacity(float pxZeroToOne){
    cmEngagedOpacity=PApplet.constrain(pxZeroToOne, 0.0f, 1.0f);
  }//+++
  
  public final void ccSetEngageMax(int pxVal){
    cmEngagedMax=pxVal&0xFFFF;
  }//+++
  
  public final void ccSetEngageCount(int pxVal){
    cmEngagedCount=pxVal&0xFFFF;
    self.setOpacity(cmEngagedCount>0?cmEngagedOpacity:cmNormalOpacity);
  }//+++
  
  public final void ccShiftEngageCount(int pxOffset){
    cmEngagedCount+=pxOffset;
    self.setOpacity(cmEngagedCount>0?cmEngagedOpacity:cmNormalOpacity);
  }//+++
  
  public final void ccDecrementEngageCount(){
    if(cmEngagedCount<=0){return;}
    ccShiftEngageCount(-1);
  }//+++
  
  public final void ccDecrementEngageCount(int pxCount){
    if(cmEngagedCount<=0){return;}
    ccShiftEngageCount(-1*(pxCount&0xFF));
  }//+++
  
  public final void ccSetupOpacity(float pxNormal, float pxEngaged){
    ccSetNormalOpacity(pxNormal);
    ccSetEngagedOpacity(pxEngaged);
  }//+++
  
  public final void ccRegisterPopupItem(JMenuItem pxItem){
    if(pxItem==null){return;}
    cmPopup.add(pxItem);
  }//+++
  
  public final void ccRefresh(){
    cmCanvas.ccRefresh();
    self.repaint();
  }
 
  
}//***

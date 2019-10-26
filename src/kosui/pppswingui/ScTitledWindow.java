/*
 * Copyright (C) 2018 Key Parker from K.I.C.
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * a undecorated frame should serve the same purpose. <br>
 * but this is how i want a floating window looks like. <br>
 */
public class ScTitledWindow extends JWindow {
  
  private JLabel cmTitle;
  private boolean cmHasCenter;
  private boolean cmHasEnd;
  private int cmAnchorX, cmAnchorY;
  private boolean cmIsFinished;

  //=== 
  
  /**
   * for windows os,
   * having shared owner makes a window little bit easy to use.<br>
   * but the situation on mac os would be pretty dizzy.<br>
   * @param pxOwner passed to super directly
   */
  public ScTitledWindow(Frame pxOwner) {
    super(pxOwner);
    cmHasCenter=false;
    cmHasEnd=false;
    cmIsFinished=false;
    cmAnchorX=0;
    cmAnchorY=0;
  }//++!
  
  /**
   * the owner will be null by default.<br>
   */
  public ScTitledWindow(){
    this(null);
  }//++!

  /**
   * 
   * @param pxTitle a drag able dummy JLabel serve as title bar
   */
  public final void ccInit(String pxTitle){
    ccInit(pxTitle, Color.DARK_GRAY);
  }//++!

  /**
   * 
   * @param pxTitle a drag able dummy JLabel serve as title bar
   * @param pxColor for content pane
   */
  public final void ccInit(String pxTitle, Color pxColor){
    final JWindow lpWindow=this;

    Container lpContentPane = lpWindow.getContentPane();
    if(lpContentPane instanceof JPanel){
      ((JPanel)lpContentPane).setBackground(pxColor);
      ((JPanel)lpContentPane).setBorder(BorderFactory.createEtchedBorder());
    }//..?

    cmTitle= new JLabel(pxTitle);
    cmTitle.setForeground(Color.WHITE);
    cmTitle.addMouseListener(new MouseAdapter() {
      @Override public void mouseReleased(MouseEvent pxE){
        cmAnchorX=0;
        cmAnchorY=0;
      }//+++
    });
    cmTitle.addMouseMotionListener(new MouseMotionAdapter() {
      @Override public void mouseDragged(MouseEvent e) {
        if(cmAnchorX==0 && cmAnchorY==0){
          cmAnchorX=e.getXOnScreen()-lpWindow.getLocationOnScreen().x;
          cmAnchorY=e.getYOnScreen()-lpWindow.getLocationOnScreen().y;
        }//..?
        lpWindow.setLocation(
          e.getXOnScreen()-cmAnchorX, 
          e.getYOnScreen()-cmAnchorY
        );
        lpWindow.toFront();
      }//+++
    });
    
    lpContentPane.add(cmTitle,BorderLayout.PAGE_START);
    
  }//++!
  
  /**
   * does some packing job.
   * need to be called after adding components.
   * @param pxVisible #
   */
  public final void ccFinish(boolean pxVisible){
    pack();
    setVisible(pxVisible);
    setAlwaysOnTop(false);
    cmHasCenter=true;
    cmHasEnd=true;
    cmIsFinished=true;
  }//++!

  /**
   * does some packing job.
   * need to be called after adding components.
   * @param pxVisible #
   * @param pxX of location
   * @param pxY of location
   */
  public final void ccFinish(boolean pxVisible,int pxX, int pxY){
    ccFinish(pxVisible);
    setLocation(pxX, pxY);
  }//++!

  /**
   * does some packing job.
   * need to be called after adding components.
   * @param pxVisible #
   * @param pxTarget serves as an anchor for relocating.
   * @param pxOffsetX see how rectangle of local UI handles this
   * @param pxOffsetY see how rectangle of local UI handles this
   */
  public final void ccFinish
    ( boolean pxVisible,
      JWindow pxTarget, 
      int pxOffsetX, 
      int pxOffsetY
    )
  { ccFinish(pxVisible);
    fsFollows(pxTarget, pxOffsetX, pxOffsetY);
  }//++!
    
  //=== 
  
  private void fsFollows(JWindow pxTarget, int pxOffsetX, int pxOffsetY){
    int lpX=pxOffsetX;
    int lpY=pxOffsetY;
    if(pxTarget!=null){
      lpX+=pxTarget.getX()+(pxOffsetY==0?pxTarget.getWidth( ):0);
      lpY+=pxTarget.getY()+(pxOffsetX==0?pxTarget.getHeight():0);
    } setLocation(lpX, lpY);
  }//+++
    
  //===
  
  /**
   * to a window a border layout is always used, this is for the center.
   * at can not be added twice.
   * @param pxContainer supposedly a panel
   */
  public final void ccAddCenter(JComponent pxContainer){
    if(!cmHasCenter){
      Container lpContentPane=this.getContentPane();
        lpContentPane.add(pxContainer,BorderLayout.CENTER);
    }cmHasCenter=true;
  }//+++

  /**
   * to a window a border layout is always used, this is for the page end.
   * at can not be added twice.
   * @param pxContainer supposedly a text field as status bar.
   */
  public final void ccAddPageEnd(JComponent pxContainer){
    if(!cmHasEnd){
      Container lpContentPane=this.getContentPane();
        lpContentPane.add(pxContainer,BorderLayout.PAGE_END);
    }cmHasEnd=true;
  }//+++
  
  //===
  
  /**
   * wrapper for JLabel::setText()
   * @param pxTitle #
   */
  public final void ccSetTitle(String pxTitle){
    if(cmTitle==null){return;}
    cmTitle.setText(pxTitle);
  }//+++

  /**
   * flips visibility. <br>
   * will be blocked out from dispatch event. <br>
   */
  public final void ccSetIsVisible(){if(ScConst.ccIsEDT()){
    boolean lpNow=isVisible();
    setVisible(!lpNow);
  }}//+++

  /**
   * will be blocked out from dispatch event. <br>
   * @param pxStatus #
   */
  public final void ccSetIsVisible(boolean pxStatus)
    {if(ScConst.ccIsEDT()){setVisible(pxStatus);}}//+++
  
  /**
   * for defending double initiation.<br>
   * @return #
   */
  public final boolean ccIsFinished(){
    return cmIsFinished;
  }//+++

}//***eof

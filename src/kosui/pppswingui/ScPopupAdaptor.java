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

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import kosui.ppplocalui.EiTriggerable;

/**
 * i used to wonder where is the `setPopupMenu` method
 *   in the component class. <br>
 * that was really awfully bloody wrong. <br>
 */
public class ScPopupAdaptor extends MouseAdapter{
  
  private final JPopupMenu cmPop;
  private final EiTriggerable cmMainAction;
  
  //=== setup
  
  /**
   * ##
   * @param pxPop the menu root for context menu to get popped up
   * @param pxClick the action for `button one` on release
   */
  public ScPopupAdaptor(JPopupMenu pxPop, EiTriggerable pxClick) {
    cmPop = pxPop;
    cmMainAction = pxClick;
  }//+++

  /**
   * ##
   * @param pxPop the menu root for pop showing
   */
  public ScPopupAdaptor(JPopupMenu pxPop) {
    this(pxPop, null);
  }//+++
  
  //===
  
  //===
  
  private boolean ssPopupShow(MouseEvent me){
    if(me==null){return false;}
    if(cmPop==null){return false;}
    if (me.isPopupTrigger()) {
      cmPop.show(
        me.getComponent(),
        me.getX(),me.getY()
      );
      me.consume();
      return true;
    }//..?
    return false;
  }//+++
  
  /**
   * in case you want trigger it from outside.
   * @param pxTarget ##
   * @param pxOffsetX ##
   * @param pxOffsetY ##
   */
  public final void ccPopupShow(Component pxTarget, int pxOffsetX, int pxOffsetY){
    if(pxTarget==null){return;}
    if(cmPop==null){return;}
    cmPop.show(
      pxTarget,
      pxTarget.getX()+pxOffsetX,
      pxTarget.getY()+pxOffsetY
    );
  }//+++
  
  //=== interface
  
  /**
   * {@inheritDoc}
   */
  @Override public void mousePressed(MouseEvent me) {
    
    //-- sub
    ssPopupShow(me);//..mac osx only triggers on pressing
    
  }//+++
  
  /**
   * {@inheritDoc}
   */
  @Override public void mouseReleased(MouseEvent me){
    
    //-- sub
    boolean lpProbe = ssPopupShow(me);
    if(lpProbe){return;}
    
    //-- main
    switch (me.getButton()) {
      case MouseEvent.BUTTON1:
        if(cmMainAction!=null){cmMainAction.ccTrigger();}
      break;
      case MouseEvent.BUTTON2:
        /*%reserved%*/
      break;
      case MouseEvent.BUTTON3:
        /*%reserved%*/
      break;
      default:break;
    }//...?
    
  }//+++
  
}//***eof

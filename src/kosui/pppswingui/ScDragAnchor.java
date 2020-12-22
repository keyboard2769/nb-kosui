/*
 * Copyright (C) 2020 Key Parker from K.I.C
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
import javax.swing.JWindow;

/**
 * a point anchors a supposedly rectangle shape while dragged. <br>
 * basically just a point takes offset from mouse point. <br>
 */
public class ScDragAnchor extends MouseAdapter{
  
  private int cmAnchorX=0, cmAnchorY=0;
  
  private int cmOffsetX=0, cmOffsetY=0;
  
  private Component cmPossibleTarget = null;

  /**
   * no owner
   */
  public ScDragAnchor() {} //++!
  
  /**
   * @param pxTarget as owner
   */
  public ScDragAnchor(Component pxTarget) { 
    this();
    if(pxTarget!=null){
      cmPossibleTarget = pxTarget;
    }//..?
  } //++!
  
  //=== manipulator
  
  /**
   * set to (0, 0).<br>
   * if not using swing component
   *   this needs to get called on mouse released.<br>
   */
  public final void ccClearAnchor(){
    cmAnchorX=0;
    cmAnchorY=0;
  }//+++
  
  /**
   * @return (0, 0) as true
   */
  public final boolean ccIsAnchorCleared(){
    return cmAnchorX==0 && cmAnchorY==0;
  }//+++
  
  /**
   * intensively for sketch use.<br>
   * may not be useful even maybe harmful for swing window.<br>
   * @param pxX #
   * @param pxY #
   */
  public final void ccSetOffset(int pxX, int pxY){
    cmOffsetX = pxX;
    cmOffsetY = pxY;
  }//++
  
  /**
   * @return as the x pix of start point of a supposedly dragged region
   */
  public final int ccGetOffsetX(){
    return cmOffsetX;
  }//+++
  
  /**
   * @return as the x pix of start point of a supposedly dragged region
   */
  public final int ccGetOffsetY(){
    return cmOffsetY;
  }//+++
  
  //=== operate
  
  /**
   * the main calculation
   * @param pxTargetOnScreenX ##
   * @param pxTargetOnScreenY ##
   * @param pxMouseOnScreenX ##
   * @param pxMouseOnScreenY ##
   */
  public final
  void ccMouseDragged(
    int pxTargetOnScreenX, int pxTargetOnScreenY,
    int  pxMouseOnScreenX, int  pxMouseOnScreenY
  ){
    if(ccIsAnchorCleared()){
      cmAnchorX = pxMouseOnScreenX - pxTargetOnScreenX;
      cmAnchorY = pxMouseOnScreenY - pxTargetOnScreenY;
    }//..?
    cmOffsetX = pxMouseOnScreenX - cmAnchorX;
    cmOffsetY = pxMouseOnScreenY - cmAnchorY;
  }//+++
  
  /**
   * self offset is passed as target origin
   * @param pxMouseOnScreenX ##
   * @param pxMouseOnScreenY ##
   */
  public final
  void ccMouseDragged(
    int  pxMouseOnScreenX, int  pxMouseOnScreenY
  ){
    ccMouseDragged(
      ccGetOffsetX(),
      ccGetOffsetY(),
      pxMouseOnScreenX,
      pxMouseOnScreenY
    );
  }//+++
  
  //=== interface
  
  /**
   * {@inheritDoc}
   */
  @Override public void mouseReleased(MouseEvent me) {
    if(me==null){return;}
    ccClearAnchor();
    if(cmPossibleTarget!=null){
      if(cmPossibleTarget instanceof JWindow){
        ((JWindow)cmPossibleTarget).toFront();
      }//..?
    }//..?
  }//+++
  
  /**
   * {@inheritDoc}
   */
  @Override public void mouseDragged(MouseEvent me) {
    if(me==null){return;}
    if(cmPossibleTarget!=null){
      ccMouseDragged(
        cmPossibleTarget.getLocationOnScreen().x,
        cmPossibleTarget.getLocationOnScreen().y,
        me.getXOnScreen(),
        me.getYOnScreen()
      );
      cmPossibleTarget.setLocation(
        cmOffsetX,
        cmOffsetY
      );
    }else{
      ccMouseDragged(
        cmOffsetX,
        cmOffsetY,
        me.getXOnScreen(),
        me.getYOnScreen()
      );
    }//..?
  }//+++
  
}//***eof

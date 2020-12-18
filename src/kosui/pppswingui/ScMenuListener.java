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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import kosui.ppplocalui.EiTriggerable;

public class ScMenuListener extends MouseAdapter{
  
  private final JPopupMenu cmPop;
  private final EiTriggerable cmMainDoing;
  private int cmAnchorX, cmAnchorY;
  
  //=== setup
  
  /**
   * ##
   * @param pxPop the menu root for context menu to get popped up
   * @param pxClick the action for `button one` on release
   */
  public ScMenuListener(JPopupMenu pxPop, EiTriggerable pxClick) {
    cmPop = pxPop;
    cmMainDoing = pxClick;
  }//+++

  /**
   * ##
   * @param pxPop the menu root for pop showing
   */
  public ScMenuListener(JPopupMenu pxPop) {
    this(pxPop, null);
  }//+++
  
  //===
  
  /**
   * the location for JPopupMenu::shows
   * @param pxX pix
   * @param pxY pix
   */
  public final void ccSetAnchor(int pxX, int pxY){
    cmAnchorX=pxX;
    cmAnchorY=pxY;
  }//+++
  
  /**
   * #
   */
  public final void ccClearAnchor(){
    ccSetAnchor(0, 0);
  }//+++
  
  /**
   * @return pix
   */
  public final int ccGetAnchorX(){
    return cmAnchorX;
  }//+++
  
  /**
   * @return pix
   */
  public final int ccGetAnchorY(){
    return cmAnchorY;
  }//+++
  
  /**
   * @return ##
   */
  public final boolean ccIsAnchorCleared(){
    return cmAnchorX==0 && cmAnchorY==0;
  }//+++
  
  //=== interface
  
  /**
   * {@inheritDoc}
   */
  @Override public void mouseReleased(MouseEvent e){
    
    //-- sub
    if (e.isPopupTrigger()) {
      cmPop.show(
        e.getComponent(),
        e.getX(),e.getY()
      );
      ccClearAnchor();
      return;
    }//..?
    
    //-- main
    if(e.getButton() == MouseEvent.BUTTON1){
      if(cmMainDoing!=null){
        cmMainDoing.ccTrigger();
      }//..?
      ccClearAnchor();
      return;
    }//..?
    
    //-- post
    ccClearAnchor();
    
  }//+++
  
}//***eof

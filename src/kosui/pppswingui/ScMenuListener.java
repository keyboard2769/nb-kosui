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

class ScMenuListener extends MouseAdapter{
  
  private final JPopupMenu cmPop;
  private int cmAnchorX, cmAnchorY;
  
  ScMenuListener(JPopupMenu pxPop) {cmPop = pxPop;}
  
  //@Override public void mousePressed(MouseEvent e){ccCheckIfShowsPop(e);}
  
  @Override public void mouseReleased(MouseEvent e){
    ssCheckIfShowsPop(e);
    ccClearAnchor();
  }//+++
  
  private void ssCheckIfShowsPop(MouseEvent e) {
    if (e.isPopupTrigger()) {
      cmPop.show(
        e.getComponent(),
        e.getX(),e.getY()
      );
    }//..?
  }//+++
  
  //===
  
  public final void ccSetAnchor(int pxX, int pxY){
    cmAnchorX=pxX;
    cmAnchorY=pxY;
  }//+++
  
  public final void ccClearAnchor(){
    ccSetAnchor(0, 0);
  }//+++
  
  public final int ccGetAnchorX(){
    return cmAnchorX;
  }//+++
  
  public final int ccGetAnchorY(){
    return cmAnchorY;
  }//+++
  
  public final boolean ccIsAnchorCleared(){
    return cmAnchorX==0 && cmAnchorY==0;
  }//+++
  
}//***eof

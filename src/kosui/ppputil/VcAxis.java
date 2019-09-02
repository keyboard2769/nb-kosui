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

package kosui.ppputil;

import processing.core.PApplet;
import static processing.core.PApplet.nf;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import static processing.core.PConstants.TOP;

/**
 * <pre>
 * if i am drawing some sketch on a paper i will never use a ruler.
 * i wish you have a better way to determine any location and size.
 * </pre>
 */
public final class VcAxis {
  
  /**
   * @return instance
   */
  public static final VcAxis ccGetInstance(){
    if(self==null){self = new VcAxis();}
    return self;
  }
  private static VcAxis self = null;
  private VcAxis(){}//..!
  
  //===

  private PApplet cmOwner=null;
  
  private int cmAxisColor=0x9933EE33;
  private boolean cmIsEnabled=true;
  private int 
    cmAnchorX=0,
    cmAnchorY=0
  ;//...
  
  /**
   * <pre>
   * it is invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * </pre>
   * @param pxParent your sketch
   * @param pxEnable #
   */
  public final void ccInit(PApplet pxParent, boolean pxEnable){
    if(cmOwner==null){cmOwner=pxParent;}
    ccSetIsEnabled(pxEnable);
  }//..!
  
  //===
  
  /**
   * 
   * @param pxColor ARGB
   */
  public final void ccSetColor(int pxColor){
    cmAxisColor=pxColor;
  }//+++
  
  /**
   * 
   * @param pxX pix
   * @param pxY pix
   */
  public final void ccSetAnchor(int pxX,int pxY){
    cmAnchorX=pxX;
    cmAnchorY=pxY;
  }//+++
  
  /**
   * flips states.
   */
  public final void ccSetIsEnabled(){
    cmIsEnabled=!cmIsEnabled;
  }//+++
  
  /**
   * 
   * @param pxStatus #
   */
  public final void ccSetIsEnabled(boolean pxStatus){
    cmIsEnabled=pxStatus;
  }//+++
  
  //===
  
  private void ssUpdate(){
    
    //-- check
    if(!cmIsEnabled){return;}
    if(cmOwner==null){return;}
    
    //-- pre
    int lpMouseX=cmOwner.mouseX;
    int lpMouseY=cmOwner.mouseY;
    int lpWidth=lpMouseX-cmAnchorX;
    int lpHeight=lpMouseY-cmAnchorY;
    boolean lpHasAnchor=(cmAnchorX!=0)&&(cmAnchorY!=0);
    
    //-- axis
    cmOwner.noFill();
    cmOwner.stroke(cmAxisColor);
    cmOwner.line(0,lpMouseY,cmOwner.width,lpMouseY);
    cmOwner.line(lpMouseX,0,lpMouseX,cmOwner.height);
    
    //-- rect
    if(lpHasAnchor){cmOwner.rect(cmAnchorX,cmAnchorY,lpWidth,lpHeight);}
    cmOwner.noStroke();
    
    //-- info
    cmOwner.fill(cmAxisColor);
    String lpMouse=nf(lpMouseX,3)+":"+nf(lpMouseY,3);
    String lpSize=nf(lpWidth,3)+":"+nf(lpHeight,3);
    cmOwner.text(lpMouse,lpMouseX+2,lpMouseY-14);
    cmOwner.textAlign(RIGHT, TOP);
    cmOwner.text(lpSize,lpMouseX-2,lpMouseY+4);
    if(lpHasAnchor){
      String lpAnchor=nf(cmAnchorX,3)+":"+nf(cmAnchorY,3);
      cmOwner.text(lpAnchor,lpMouseX-2,lpMouseY-14);
    }//..?
    cmOwner.textAlign(LEFT, TOP);
  
  }//+++
  
  /**
   * <b>MUST BE INITIATED</b><br>
   * should be called inside draw()<br>
   */
  static public void ccUpdate(){
    self.ssUpdate();
  }//+++
  
}//***eof

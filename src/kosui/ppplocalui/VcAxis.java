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

package kosui.ppplocalui;

import processing.core.PApplet;
import static processing.core.PApplet.nf;
import static processing.core.PApplet.LEFT;
import static processing.core.PApplet.RIGHT;
import static processing.core.PApplet.TOP;

/**
 * if i am drawing some sketch on a paper i will never use a ruler.<br>
 * i wish you have a better way to determine any location and size.<br>
 */
public final class VcAxis {
  
  private static PApplet pbOwner=null;
  
  private static int pbAxisColor=0x9933EE33;
  private static boolean pbIsEnabled=true;
  private static int 
    pbAnchorX=0,
    pbAnchorY=0
  ;//...
  
  //===
  
  private VcAxis (){}//+++
  
  //===
  
  /**
   * it is invoked from the factory's initiator.
   * you don't have to call this in your sketch;
   * @param pxParent your sketch
   */
  static public void ccInit(PApplet pxParent)
    {pbOwner=pxParent;}//+++
  
  //===
  
  /**
   * 
   * @param pxColor #
   */
  static public void ccSetColor(int pxColor)
    {pbAxisColor=pxColor;}//+++
  
  /**
   * 
   * @param pxX #
   * @param pxY #
   */
  static public void ccSetAnchor(int pxX,int pxY)
    {pbAnchorX=pxX;pbAnchorY=pxY;}//+++
  
  /**
   * flips states.
   */
  static public void ccSetIsEnabled()
    {pbIsEnabled=!pbIsEnabled;}//+++
  
  /**
   * 
   * @param pxStatus #
   */
  static public void ccSetIsEnabled(boolean pxStatus)
    {pbIsEnabled=pxStatus;}//+++
  
  //===
  
  /**
   * should be called inside draw()
   */
  static public void ccUpdate(){
    
    if(!pbIsEnabled){return;}
    
    int lpMouseX=pbOwner.mouseX;
    int lpMouseY=pbOwner.mouseY;
    int lpWidth=lpMouseX-pbAnchorX;
    int lpHeight=lpMouseY-pbAnchorY;
    boolean lpHasAnchor=(pbAnchorX!=0)&&(pbAnchorY!=0);
    
    //-- axis
    pbOwner.noFill();
    pbOwner.stroke(pbAxisColor);
    pbOwner.line(0,lpMouseY,pbOwner.width,lpMouseY);
    pbOwner.line(lpMouseX,0,lpMouseX,pbOwner.height);
    
    //-- rect
    if(lpHasAnchor){pbOwner.rect(pbAnchorX,pbAnchorY,lpWidth,lpHeight);}
    pbOwner.noStroke();
    
    //-- info
    pbOwner.fill(pbAxisColor);
    String lpMouse=nf(lpMouseX,3)+":"+nf(lpMouseY,3);
    String lpSize=nf(lpWidth,3)+":"+nf(lpHeight,3);
    pbOwner.text(lpMouse,lpMouseX+2,lpMouseY-14);
    pbOwner.textAlign(RIGHT, TOP);
    pbOwner.text(lpSize,lpMouseX-2,lpMouseY+4);
    if(lpHasAnchor){
      String lpAnchor=nf(pbAnchorX,3)+":"+nf(pbAnchorY,3);
      pbOwner.text(lpAnchor,lpMouseX-2,lpMouseY-14);
    }//..?
    pbOwner.textAlign(LEFT, TOP);
    
  }//+++
  
}//***eof

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

import kosui.ppplocalui.EcPoint;
import processing.core.PApplet;

/**
 * <pre>
 * if i am drawing some sketch on a paper i will never use a ruler.
 * i wish you have a better way to determine any location and size.
 * </pre>
 */
public final class VcLocalAxis {
  
  /**
   * @return instance
   */
  public static final VcLocalAxis ccGetInstance(){
    if(self==null){self = new VcLocalAxis();}
    return self;
  }
  private static VcLocalAxis self = null;
  private VcLocalAxis(){}//..!
  
  //===

  private PApplet cmOwner=null;
  
  private int cmAxisColor=0x9933EE33;
  private boolean cmIsEnabled=true;
  private int 
    cmAnchorX=0,
    cmAnchorY=0
  ;//...
  
  private EcPoint cmTransformedMouse = null;
  
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
  
  /**
   * will get shown on east south point
   * @param pxStatus ##
   */
  public final void ccSetTransformed(boolean pxStatus){
    if(pxStatus){
      cmTransformedMouse=new EcPoint(0, 0);
    }else{
      cmTransformedMouse=null;
    }//..?
  }//+++
  
  /**
   * ##
   * @param pxX ##
   * @param pxY ##
   */
  public final void ccSetTransformedMosuePoint(int pxX, int pxY){
    if(cmTransformedMouse==null){return;}
    cmTransformedMouse.ccSetUnlimittedLocation(pxX, pxY);
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
    
    //-- info ** pre
    cmOwner.fill(cmAxisColor);
    
    //-- info ** draw ** mosue
    String lpMouse=VcStringUtility.ccPackupDimensionValue(lpMouseX, lpMouseY);
    cmOwner.textAlign(PApplet.LEFT, PApplet.BOTTOM);
    cmOwner.text(lpMouse,lpMouseX+2,lpMouseY-2);
    
    //-- info ** draw ** select
    if(lpHasAnchor){
      cmOwner.textAlign(PApplet.RIGHT, PApplet.TOP);
      String lpSize=VcStringUtility
        .ccPackupDimensionValue(lpWidth, lpHeight);
      cmOwner.text(lpSize,lpMouseX-2,lpMouseY+4);
      String lpAnchor=VcStringUtility
        .ccPackupDimensionValue(cmAnchorX, cmAnchorY);
      cmOwner.text(lpAnchor,lpMouseX-2,lpMouseY-14);
    }//..?
    
    //-- info ** draw ** transform
    if(cmTransformedMouse!=null){
      cmOwner.textAlign(PApplet.LEFT, PApplet.TOP);
      String lpTransformed=VcStringUtility.ccPackupDimensionValue(
        cmTransformedMouse.ccGetX(),
        cmTransformedMouse.ccGetY()
      );cmOwner.text(lpTransformed,lpMouseX+2,lpMouseY+2);
    }//..?
    
    //-- always
    cmOwner.textAlign(PApplet.LEFT, PApplet.TOP);
    
  }//+++
  
  /**
   * <b>MUST BE INITIATED</b><br>
   * should be called inside draw()<br>
   */
  static public void ccUpdate(){
    self.ssUpdate();
  }//+++
  
  /**
   * <b>MUST BE INITIATED</b><br>
   * should be called inside draw()<br>
   * the axis him self does NOT calcualte transformation.<br>
   * @param pxTransformedX
   * @param pxTransformedY 
   */
  static public void ccUpdate(int pxTransformedX, int pxTransformedY){
    self.ccSetTransformedMosuePoint(pxTransformedX, pxTransformedY);
    self.ssUpdate();
  }//+++
  
}//***eof

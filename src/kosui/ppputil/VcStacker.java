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
import static processing.core.PConstants.BOTTOM;
import static processing.core.PConstants.LEFT;
import static processing.core.PApplet.constrain;

import kosui.pppmodel.McLineStacker;

/**
 * i used to wonder how should i handle the in window print problem, 
 * i gave the scroll thing up, and i got this.<br>
 * this is a wrapper for McLineStacker.<br>
 */
public final class VcStacker {
  
  /**
   * @return instance
   */
  public static final VcStacker ccGetInstance(){return SELF;}
  private static final VcStacker SELF = new VcStacker();
  private VcStacker (){}//+++
  
  //===
  
  private PApplet pbOwner=null;
  
  /**
   * will be shown after cleared 
   */
  private  String pbDefaultMessage="::";
  
  private boolean pbIsVisible=true;
  
  private int
    //-- color
    pbBaseColor=0xFF33EE33,
    //-- pix
    pbReversedOffsetY=40,
    //-- count
    pbCharCount=48
  ;//...
  
  private final McLineStacker O_STK
    = new McLineStacker();
  
  //===
  
  /**
   * <pre>
   * it is invoked from the manager's initiator.
   * you might have to call this in your sketch.
   * </pre>
   * @param pxOwner your sketch
   */
  public final void ccInit(PApplet pxOwner){
    if(pxOwner==null){return;}
    if(pbOwner==null){pbOwner=pxOwner;}
  }//..!
  
  /**
   * @param pxColor both text and base line will be this color
   */
  public final void ccSetBaseColor(int pxColor){
    pbBaseColor=pxColor;
  }//+++
  
  /**
   * @param pxSize #
   * @param pxDivisor #
   */
  public final void ccSetTrim(int pxSize, int pxDivisor){
    O_STK.ccSetTrim(pxSize, pxDivisor);
  }//+++
  
  /**
   * <pre>
   * a base line will be placed on the scree,
   *   it is also where the text starts.
   * </pre>
   * @param pxOffsetY to the bottom
   */
  public final void ccSetReversedOffsetY(int pxOffsetY){
    pbReversedOffsetY=pxOffsetY;
  }//+++
  
  /**
   * max character number of a single line.<br>
   * @param pxCount 3~512
   */
  public final void ccSetCharCount(int pxCount){
    pbCharCount=constrain(pxCount, 3, 512);
  }//+++
  
  /**
   * @param pxState #
   */
  public final void ccSetVisible(boolean pxState){
    pbIsVisible=pxState;
  }//+++
  
  /**
   * flip version
   */
  public final void ccFlipVisible(){
    pbIsVisible=!pbIsVisible;
  }//+++
  
  //===
  
  /**
   * supposedly should get called inside PApplet.draw() loop
   */
  public static final void ccUpdate(){
    SELF.ssUpdate();
  }//+++
  
  private void ssUpdate(){
    if(pbOwner==null || !pbIsVisible){return;}
    int lpOffset=pbOwner.height-pbReversedOffsetY;
    pbOwner.pushStyle();{
      pbOwner.fill(pbBaseColor);
      pbOwner.textAlign(LEFT, BOTTOM);
      pbOwner.text(O_STK.ccGetStacked(),2,lpOffset);
      pbOwner.stroke(pbBaseColor);
      pbOwner.line(0,lpOffset,pbOwner.width,lpOffset);
    }pbOwner.popStyle();
  }//+++
  
  /**
   * will get passed to McLineStacker.ccStack() directly.<br>
   * @param pxTag #
   * @param pxVal #
   */
  static public final void ccStack(String pxTag, Object pxVal){
    SELF.O_STK.ccStack(pxTag, pxVal);
  }//+++
  
  /**
   * a wrapped input will get passed to McLineStacker.ccStack() directly.<br>
   * @param pxLine #
   */
  static public final void ccStack(String pxLine){
    SELF.O_STK.ccStack(VcStringUtility.ccWrap(pxLine, SELF.pbCharCount));
  }//+++
  
  /**
   * set stacked to default message held by the system stacker.
   */
  static public final void ccClear(){
    SELF.O_STK.ccClear(SELF.pbDefaultMessage);
  }//+++
  
  /**
   * @param pxDefault will replace current default message
   */
  static public final void ccClear(String pxDefault){
    SELF.pbDefaultMessage=VcStringUtility.ccWrap(pxDefault, SELF.pbCharCount);
    SELF.O_STK.ccClear(SELF.pbDefaultMessage);
  }//+++
  
  //===
  
  /**
   * @return McLineStacker.ccGetSize()
   */
  public final int ccGetSize(){
    return O_STK.ccGetSize();
  }//+++
  
}//***eof

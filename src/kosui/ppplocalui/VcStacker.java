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

import kosui.ppputil.McLineStacker;
import kosui.ppputil.VcConst;

import processing.core.PApplet;
import static processing.core.PConstants.BOTTOM;
import static processing.core.PConstants.LEFT;
import static processing.core.PApplet.constrain;

/**
 * i used to wonder how should i handle the in window print problem, 
 * i gave the scroll thing up, and i got this.<br>
 * this is a wrapper for McLineStacker.<br>
 */
public final class VcStacker {
  
  static private PApplet pbOwner=null;
  
  /**
   * will be shown after cleared 
   */
  static public String pbDefaultMessage="::";
  
  static private boolean pbIsVisible=true;
  static private int pbBaseColor=0xFF33EE33;
  static private int pbReversedOffsetY=40;
  static private int pbCharCount=48;
  
  static private final McLineStacker O_STK=new McLineStacker();
  
  //===
  
  private VcStacker (){}//+++
  
  /**
   * it is invoked from the factory's initiator.
   * you don't have to call this in your sketch.<br>
   * @param pxOwner your sketch
   */
  static public final void ccInit(PApplet pxOwner)
    {pbOwner=pxOwner;}
  
  //=== 
  
  /**
   * 
   * @param pxColor both text and base line will be this color
   */
  static public final void ccSetBaseColor(int pxColor)
    {pbBaseColor=pxColor;}//+++
  
  /**
   * @param pxSize #
   * @param pxDivisor #
   */
  static public final void ccSetTrim(int pxSize, int pxDivisor){
    O_STK.ccSetTrim(pxSize, pxDivisor);
  }//+++
  
  /**
   * a base line will be placed on the scree,
   * it is also where the text starts.
   * @param pxOffsetY to the bottom
   */
  static public final void ccSetReversedOffsetY(int pxOffsetY)
    {pbReversedOffsetY=pxOffsetY;}//+++
  
  /**
   * max character number of a single line.<br>
   * will be constrained to 3-512;
   * @param pxCount #
   */
  static public final void ccSetCharCount(int pxCount){
    pbCharCount=constrain(pxCount, 3, 512);
  }//+++
  
  /**
   * 
   * @param pxState #
   */
  static public final void ccSetVisible(boolean pxState)
    {pbIsVisible=pxState;}//+++
  
  /**
   * flip version
   */
  static public final void ccFlipVisible()
    {pbIsVisible=!pbIsVisible;}//+++
  
  //===
  
  /**
   * should be called inside draw()
   */
  static public final void ccUpdate(){
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
   * @param pxTag #
   * @param pxVal #
   */
  static public final void ccStack(String pxTag, Object pxVal){
    O_STK.ccStack(pxTag, pxVal);
  }//+++
  
  /**
   * a wrapper of to string method.
   * @param pxVal #
   */
  static public final void ccStack(Object pxVal){
    ccStack(pxVal.toString());
  }//+++
  
  /**
   * @param pxVal #
   */
  static public final void ccStack(String pxVal){
    O_STK.ccStack(
      VcConst.ccWrap(pxVal, pbCharCount)
    );
  }//+++
  
  /**
   * set stacked to default message held by the system stacker
   */
  static public final void ccClear(){O_STK.ccClear(pbDefaultMessage);}//+++
  
  /**
   * @param pxDefault will replace current default message
   */
  static public final void ccClear(String pxDefault){
    pbDefaultMessage=VcConst.ccWrap(pxDefault, pbCharCount);
    O_STK.ccClear(pbDefaultMessage);
  }//+++
  
  //===
  
  /**
   * @return #
   */
  static public final int ccGetSize()
    {return O_STK.ccGetSize();}
  
}//***eof

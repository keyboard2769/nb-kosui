/*
 * Copyright (C) 2018 Key Parker from K.I.C
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

import kosui.ppputil.VcStringUtility;

/**
 * a lamp don't react at you, it just shows you if it is on. <br>
 * there is no any difference with element, what a horrible hierarchy. <br>
 */
public class EcLamp extends EcElement{
  
  /**
   * for inner use
   */
  public static final int
    //-- pix
    C_STROKE_THICK   = 4,
    C_DEFAULT_SCALE  = 18
  ;//...
  
  /**
   * can get applied to a line or just a bigger rectangle
   */
  protected int cmBorderColor = 0xFF555555;

  /**
   * the default size is 18*18 pix;
   */
  public EcLamp(){
    super();
    ccSetSize(C_DEFAULT_SCALE, C_DEFAULT_SCALE);
  }//..!
  
  /**
   * inherited
   * @param pxKey #
   * @param pxID #
   */
  public EcLamp(String pxKey, int pxID){
    super();
    ccSetupKey(VcStringUtility.ccNulloutString(pxKey));
    ccSetID(pxID);
    ccSetSize(C_DEFAULT_SCALE, C_DEFAULT_SCALE);
    if(cmText.length()>1){cmText=cmText.substring(0, 1);}
  }//..!
  
  /**
   * output component can have no identical id.<br>
   * @param pxKey will get passed to setter directly
   */
  public EcLamp(String pxKey){
    super();
    ccSetupKey(pxKey);
    ccSetSize(C_DEFAULT_SCALE, C_DEFAULT_SCALE);
    if(cmText.length()>1){cmText=cmText.substring(0, 1);}
  }//..!
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate() {
    if(!ccIsVisible()){return;}
    drawRoundLamp(ccActColor());
    drawText(cmTextColor);
    drawName(cmNameColor);
  }//+++
  
  /**
   * inward use only
   * @param pxColor #
   */
  protected void drawRoundLamp(int pxColor){
    
    int lpCenterX=ccCenterX();
    int lpCenterY=ccCenterY();
    
    pbOwner.fill(cmBorderColor);
    pbOwner.ellipse(lpCenterX,lpCenterY,cmW,cmH);

    pbOwner.fill(pxColor);
    pbOwner.ellipse(
      lpCenterX,
      lpCenterY,
      cmW-C_STROKE_THICK,
      cmH-C_STROKE_THICK
    );
    
  }//+++
  
  /**
   * @param pxColor ARPG
   */
  public final void ccSetBorderColor(int pxColor){
    cmBorderColor=pxColor;
  }//+++
  
}//***eof

/*
 * Copyright (C) 2019 Key Parker from K.I.C
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

import java.awt.Color;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import kosui.ppputil.VcNumericUtility;
import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

/**
 * a gauge might be easy to understand be hard to draw.<br>
 * i really did not want to commit it into the library package.<br>
 */
public class ScGauge extends JProgressBar{
  
  private float cmSpan;
  private final String cmUnit;
  private final String cmKey;
  private String cmText;
  private Color cmBackgroundColor = ScConst.C_LIT_GRAY;
  private Color cmForeGroundColor = ScConst.C_DARK_YELLOW;
  private Color cmAlertColor = ScConst.C_DARK_ORANGE;
  
  //=== 
  
  /**
   * @param pxName will get nulled out
   * @param pxUnit will get nulled out 
   */
  public ScGauge(String pxName, String pxUnit){
    super(SwingConstants.HORIZONTAL,0,100);
    cmKey=VcStringUtility.ccNulloutString(pxName);
    cmText=cmKey;
    cmUnit=VcStringUtility.ccNulloutString(pxUnit);
    cmSpan=100f;
    ssInit();
  }//..!
  
  private void ssInit(){
    setValue(2);
    setString(cmText+cmUnit);
    setBorderPainted(true);
    setStringPainted(true);
    setBackground(cmBackgroundColor);
    setForeground(cmForeGroundColor);
  }//++!
  
  //===
  
  /**
   * @param pxColor do not pass null
   */
  public final void ccSetBackgroundColor(Color pxColor){
    if(pxColor==null){return;}
    cmBackgroundColor=pxColor;
  }//+++
  
  /**
   * @param pxColor do not pass null
   */
  public final void ccSetForeGroundColor(Color pxColor){
    if(pxColor==null){return;}
    cmForeGroundColor=pxColor;
  }//+++
  
  /**
   * @param pxColor do not pass null
   */
  public final void ccSetAlertColor(Color pxColor){
    if(pxColor==null){return;}
    cmAlertColor=pxColor;
  }//+++
  
  /**
   * @param pxBack ##
   * @param pxFore ##
   * @param pxAlert ##
   */
  public final void ccSetupColor(Color pxBack, Color pxFore, Color pxAlert){
    ccSetBackgroundColor(pxBack);
    ccSetForeGroundColor(pxFore);
    ccSetAlertColor(pxAlert);
  }//+++
  
  /**
   * @param pxSpan must be bigger than 1f
   */
  public final void ccSetSpan(float pxSpan){
    cmSpan=pxSpan<1f?1f:pxSpan;
  }//+++
  
  /**
   * @param pxValue will get constrained to 0-10 via PApplet.constrain
   */
  public final void ccSetPercentage(int pxValue){
    int lpPercentage=PApplet.constrain(pxValue,0,100);
    float lpReal=cmSpan*lpPercentage/100;
    setValue(lpPercentage);
    setString(cmText
      +Float.toString(VcNumericUtility.ccRoundForOneAfter(lpReal))
      +cmUnit
    );
  }//+++
  
  /**
   * @param pxValue will get constrained to span x20 via PApplet.constrain
   */
  public final void ccSetValue(int pxValue){
    float lpReal=PApplet.constrain(pxValue, 0, cmSpan*20);
    int lpPercentage=PApplet.ceil(100*lpReal/(cmSpan*10));
    setValue(lpPercentage);
    setString(cmText+":"+VcNumericUtility.ccFormatPointTwoFloat(lpReal)+cmUnit);
  }//+++
  
  /**
   * @param pxStatus turn it to the alert color or not
   */
  public final void ccSetAlert(boolean pxStatus){
    setForeground(pxStatus?cmAlertColor:cmForeGroundColor);
  }//+++
  
  /**
   * @param pxText will get nulled out
   */
  public final void ccSetText(String pxText){
    cmText=VcStringUtility.ccNulloutString(pxText);
  }//+++
  
  /**
   * @return ##
   */
  public final String ccGetKey(){
    return cmKey;
  }//+++
  
}//***eof

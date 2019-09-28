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
  
  /**
   * hard coded as byte
   */
  public static final int C_VAL_MASK = 0xFF;
  
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
    super(SwingConstants.HORIZONTAL,0,C_VAL_MASK);
    cmKey=VcStringUtility.ccNulloutString(pxName);
    cmText=cmKey;
    cmUnit=VcStringUtility.ccNulloutString(pxUnit);
    this.setValue(99);
    this.setString(cmText+":?"+cmUnit);
    this.setBorderPainted(true);
    this.setStringPainted(true);
    this.setBackground(cmBackgroundColor);
    this.setForeground(cmForeGroundColor);
  }//..!
  
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
   * the gauge will be full as this value equals 255.<br>
   * @param pxByte : will get masked to 0-255
   */
  public final void ccSetPercentage(int pxByte){
    setValue(pxByte&C_VAL_MASK);
  }//+++
  
  /**
   * <b>DOUBLE ALIASING PROPORTION METHOD OF NUMERIC UTILITY</b><br>
   * @param pxZeroToOne : 0.00 - 1.00f
   */
  public final void ccSetPercentage(float pxZeroToOne){
    setValue(VcNumericUtility.ccProportion(pxZeroToOne));
  }//+++
  
  /**
   * only change the text of this component.<br>
   * the text is constructed via String.format, you DO pay its overhead.<br>
   * @param pxValue can be anything
   */
  public final void ccSetValue(int pxValue){
    setString(String.format("%s:%d%s", cmText,pxValue,cmUnit));
  }//+++
  
  /**
   * only change the text of this component.<br>
   * the text is constructed via String.format, you DO pay its overhead.<br>
   * @param pxValue can be anything
   */
  public final void ccSetValue(float pxValue){
    setString(String.format("%s:%f%s", cmText,pxValue,cmUnit));
  }//+++
  
  /**
   * only change the text of this component.<br>
   * the text is constructed via String.format, you DO pay its overhead.<br>
   * @param pxValue can be anything
   */
  public final void ccSetFloatValueForOneAfter(float pxValue){
    setString(String.format("%s:%.1f%s", cmText,pxValue,cmUnit));
  }//+++
  
  /**
   * only change the text of this component.<br>
   * the text is constructed via String.format, you DO pay its overhead.<br>
   * @param pxValue can be anything
   */
  public final void ccSetFloatValueForTwoAfter(float pxValue){
    setString(String.format("%s:%.2f%s", cmText,pxValue,cmUnit));
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

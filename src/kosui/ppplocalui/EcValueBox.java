/*
 * Copyright (C) 2018 Key Parker
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

import kosui.ppputil.VcConst;
import kosui.ppputil.VcStringUtility;
import processing.core.PApplet;

/**
 * a value box shows some value with unit. <br>
 * and for some reason, i don't like shadows now. <br>
 */
public class EcValueBox extends EcElement{
  
  //===

  /**
   * will be passed to nf() or nfc()
   */
  protected int cmDigit=1;

  /**
   * if you don't need it give a space at least
   */
  protected String cmUnit=" ";
  
  //===
  
  /**
   * @param pxKey will get passed to key and name separately
   * @param pxForm serves as initiated text and last two letter will be unit
   * @param pxID will get passed to setter directly
   */
  public EcValueBox(String pxKey, String pxForm, int pxID){
    super();
    ccSetKey(pxKey);
    ccSetName(pxKey);
    ccSetText(pxForm);
    ccSetTextAlign('r');
    ccSetID(pxID);
    ccSetSize();
    ccSetTextColor(EcConst.C_LIT_GRAY);
    ccSetColor(EcConst.ccAdjustColor(cmOffColor, 0x20));
    ccSetUnit(VcStringUtility.ccExtracUnit(pxForm));
    cmDigit=(cmText.length()-cmUnit.length()-1);
    if(pxForm.startsWith("-") || pxForm.startsWith("+")){cmDigit--;}
    if(cmDigit<1){cmDigit=1;}
  }//..!
  
  /**
   * will have no identical id.<br>
   * @param pxKey will get passed to key and name separately
   * @param pxForm serves as initiated text and last two letter will be unit
   */
  public EcValueBox(String pxKey, String pxForm){
    this(pxKey, pxForm, EcConst.C_ID_IGNORE);
  }//..!
  
  /**
   * will have an empty string as key.<br>
   * will have no identical id.<br>
   * default for is "0000 d".<br>
   */
  public EcValueBox(){
    this("", "0000 d");
  }//..!
  
  
  //===
  
  /**
   * {@inheritDoc} 
   */
  @Override public void ccUpdate(){
    if(!ccIsVisible()){return;}
    drawDefaultValueBox();
    drawText(cmTextColor);
    drawName(cmNameColor);
  }//+++
  
  /**
   * internal use only
   */
  protected final void drawDefaultValueBox(){
    
    pbOwner.stroke(0xCC);
    pbOwner.fill(cmIsActivated?cmOnColor:cmOffColor);
    pbOwner.rect(cmX, cmY, cmW-1, cmH-1);
    // ^.. adjust is needed whenever there is a stroke 
    pbOwner.noStroke();

  }//+++
  
  //===
  
  /**
   * a current digit value will be applyed
   * @param pxVal #
   */
  public final void ccSetValue(int pxVal){
    cmText=PApplet.nf(pxVal, cmDigit)+" "+cmUnit;
  }//+++

  /**
   * digit value is supposed to be at 0-8. 
   * this does not check, do not get over. 
   * @param pxVal #
   * @param pxDigit # 
   */
  public final void ccSetValue(int pxVal, int pxDigit){
    cmDigit=pxDigit;
    cmText=PApplet.nf(pxVal, cmDigit)+" "+cmUnit;
  }//+++

  /**
   * only change the text of this box.<br>
   * the text is constructed via String.format, you DO pay its overhead.<br>
   * @param pxValue can be anything
   */
  public final void ccSetValue(float pxValue){
    ccSetText(String.format("%f %s",pxValue,cmUnit));
  }//+++
  
  /**
   * only change the text of this box.<br>
   * the text is constructed via String.format, you DO pay its overhead.<br>
   * @param pxValue can be anything
   */
  public final void ccSetFloatValueForOneAfter(float pxValue){
    ccSetText(String.format("%.1f %s",pxValue,cmUnit));
  }//+++
  
  /**
   * only change the text of this box.<br>
   * the text is constructed via String.format, you DO pay its overhead.<br>
   * @param pxValue can be anything
   */
  public final void ccSetFloatValueForTwoAfter(float pxValue){
    ccSetText(String.format("%.2f %s",pxValue,cmUnit));
  }//+++

  /**
   * a unit is jsut a string attached at bottom. 
   * @param pxUnit #
   */
  public final void ccSetUnit(String pxUnit){
    if(!VcConst.ccIsValidString(pxUnit)){return;}
    cmUnit=pxUnit;
  }//+++
  
  /**
   * @param pxDigit will get masked to 0-7
   */
  public final void ccSetDigit(int pxDigit){
    cmDigit=pxDigit&0x7;
  }//+++

}//***eof

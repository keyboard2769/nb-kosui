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
import static processing.core.PApplet.nf;
import static processing.core.PApplet.nfc;

/**
 * a value box shows some value with unit. <br>
 * and for some reason, i don't like shadows now. <br>
 */
public class EcValueBox extends EcElement{
  
  //===

  /**
   * will be passed to both nf() and nfc()
   */
  protected int cmDigit=4;

  /**
   * if you don't need it give a space at least
   */
  protected String cmUnit=" ";
  
  //===

  /**
   * default text align is right.<br>
   */
  public EcValueBox(){
    super();
    ccSetTextAlign('r');
    ccSetTextColor(EcConst.C_LIT_GRAY);
  }//..!
  
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
    ccSetUnit(VcStringUtility.ccExtracUnit(pxForm));
    ccSetTextColor(EcConst.C_LIT_GRAY);
  }//..!
  
  /**
   * output component can have no identical id.<br>
   * @param pxKey will get passed to key and name separately
   * @param pxForm serves as initiated text and last two letter will be unit
   */
  public EcValueBox(String pxKey, String pxForm){
    super();
    ccSetKey(pxKey);
    ccSetName(pxKey);
    ccSetText(pxForm);
    ccSetTextAlign('r');
    ccSetSize();
    ccSetUnit(VcStringUtility.ccExtracUnit(pxForm));
    ccSetTextColor(EcConst.C_LIT_GRAY);
  }//..!
  
  //===
  
  /**
   * {@inheritDoc} 
   */
  @Override public void ccUpdate(){

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
    pbOwner.rect(cmX, cmY, cmW, cmH);
    pbOwner.noStroke();

  }//+++
  
  //===
  
  /**
   * a current digit value will be applyed
   * @param pxVal #
   */
  public final void ccSetValue(int pxVal){
    cmText=nf(pxVal, cmDigit)+" "+cmUnit;
  }//+++

  /**
   * digit value is supposed to be at 0-8. 
   * this does not check, do not get over. 
   * @param pxVal #
   * @param pxDigit # 
   */
  public final void ccSetValue(int pxVal, int pxDigit){
    cmDigit=pxDigit;
    cmText=nf(pxVal, cmDigit)+" "+cmUnit;
  }//+++

  /**
   * digit value is supposed to be at 0-8. 
   * this does not check, do not get over. 
   * @param pxVal #
   * @param pxDigit # 
   */
  public final void ccSetValue(float pxVal, int pxDigit){
    cmDigit=pxDigit;
    cmText=nfc(pxVal, cmDigit)+" "+cmUnit;
  }//+++

  /**
   * a unit is jsut a string attached at bottom. 
   * @param pxUnit #
   */
  public final void ccSetUnit(String pxUnit){
    if(!VcConst.ccIsValidString(pxUnit)){return;}
    cmUnit=pxUnit;
  }//+++

}//***eof

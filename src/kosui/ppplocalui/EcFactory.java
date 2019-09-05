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

package kosui.ppplocalui;

/**
 * a factory is not a all mighty all knowing something.<br>
 * it only constructs and setups products.<br>
 */
public final class EcFactory {
  
  private EcFactory(){}//+++ 
  
  //=== ** 
  
  //=== ** button
  
  //[PLAN]:: ..MomentarySW()
  //[PLAN]:: ..AlternateSW()
  
  //=== ** lamp
  
  /**
   * @param pxKey will get passed to constructor directly
   * @param pxID will get passed to constructor directly
   * @param pxLetter must be alphabetic or digit
   * @param pxSideSize make it a square
   * @return #
   */
  static public final
  EcElement ccCreateSingleLetterPL(
    String pxKey, int pxID,
    char pxLetter, int pxSideSize
  ){
    EcElement lpRes=new EcElement(pxKey, pxID);
    ccSetupSingleLetterPL(lpRes, pxLetter, pxSideSize);
    return lpRes;
  }//+++
  
  /**
   * @param pxTarget #
   * @param pxLetter must be alphabetic or digit
   * @param pxSideSize make it a square
   */
  static public final
  void ccSetupSingleLetterPL(
    EcElement pxTarget, char pxLetter, int pxSideSize
  ){
    char lpFixed = pxLetter;
    if(!Character.isAlphabetic(pxLetter)
     ||!Character.isDigit(lpFixed)){
      lpFixed='?';
    }//..?
    pxTarget.ccSetText(String.valueOf(lpFixed));
    pxTarget.ccSetNameAlign('x');
    pxTarget.ccSetTextAlign('c');
    pxTarget.ccSetSize(pxSideSize,pxSideSize);
  }//+++
  
  //[PLAN]:: ..ActuatorPL()
  //[PLAN]:: ..IndicatorPL()
  
  //=== ** box
  
  //[PLAN]:: ..InputTB()
  //[PLAN]:: ..OutputCB()
  
  //=== ** gauge
  
  /**
   * <pre>
   * for general levelor use.
   * the content color is hard coded yellow. (red if engaged)
   * the background color is hard coded black.
   * the border color is hard coded light gray.
   * </pre>
   * @param pxTarget #
   * @param pxHasStroke #
   * @param pxIsVertical #
   * @param pxW pix
   * @param pxH pix
   */
  static public
  void ccSetupContentLV(
    EcGauge pxTarget,
    boolean pxHasStroke, boolean pxIsVertical, int pxW, int pxH
  ){
    pxTarget.ccSetSize(pxW, pxH);
    pxTarget.ccSetColor(0xFFEE3333,0xFFEEEE33);
    pxTarget.ccSetIsVertical(pxIsVertical);
    pxTarget.ccSetHasStroke(pxHasStroke);
    pxTarget.ccSetGaugeColor(0xFF111111, 0xFFCCCCCC);
    pxTarget.ccSetPercentage(32);
  }//+++
    
  /**
   * <pre>
   * for general adjustor use.
   * the content color is referenced light gray. (red if engaged)
   * the background color is referenced dim gray.
   * the border color is hard referenced light gray.
   * </pre>
   * @param pxTarget #
   * @param pxHasStroke #
   * @param pxIsVertical #
   * @param pxW pix
   * @param pxH pix
   */
  static public
  void ccSetupConfigSlider(
    EcSlider pxTarget,
    boolean pxHasStroke, boolean pxIsVertical, int pxW, int pxH
  ){
    pxTarget.ccSetSize(pxW, pxH);
    pxTarget.ccSetColor(EcConst.C_RED,EcConst.C_LIT_GRAY);
    pxTarget.ccSetIsVertical(pxIsVertical);
    pxTarget.ccSetHasStroke(pxHasStroke);
    pxTarget.ccSetGaugeColor(EcConst.C_DIM_GRAY, EcConst.C_LIT_GRAY);
    pxTarget.ccSetPercentage(63);
  }//+++
  
}//***eof

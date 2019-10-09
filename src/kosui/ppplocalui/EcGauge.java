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

import kosui.ppputil.VcNumericUtility;

/**
 * a gauge can be filled.<br>
 * some one call this "bar" also.<br>
 */
public class EcGauge extends EcElement {
  
  /**
   * hard coded as byte
   */
  public static final int C_VAL_MASK = 0xFF;
  
  /**
   * setting
   */
  protected boolean
    cmHasStroke=true,
    cmIsVertical=true
  ;//--
  
  /**
   * setting
   */
  protected int
    cmBackColor   =0xFF111111,
    cmStrokeColor =0xFFCCCCCC
  ;//--
  
  /**
   * initiated value is hard coded to 99
   */
  protected int cmContentValue = 99;

  //===
  
  /**
   * @param pxKey will get passed to super directly
   * @param pxID will get passed to super directly and also effect enability
   * @param pxVert could be anything
   * @param pxW greater than zero or nothing
   * @param pxH greater than zero or nothing
   */
  public EcGauge(String pxKey, int pxID, boolean pxVert, int pxW, int pxH){
    super(pxKey, pxID);
    ccSetIsVertical(pxVert);
    ccSetIsEnabled(false);
    if(pxW>0 && pxH>0){
      ccSetSize(pxW, pxH);
    }//..?
    ccSetTextAlign('x');
  }//++!
  
  /**
   * will have an empty string as key.<br>
   * will have no identical id.<br>
   * @param pxVert could be anything
   * @param pxW greater than zero or nothing
   * @param pxH greater than zero or nothing
   */
  public EcGauge(boolean pxVert, int pxW, int pxH){
    this("", EcConst.C_ID_IGNORE, pxVert, pxW, pxH);
  }//++!
  
  /**
   * will have an empty string as key.<br>
   * will have no identical id.<br>
   * is vertical by default.<br>
   * @param pxW greater than zero or nothing
   * @param pxH greater than zero or nothing
   */
  public EcGauge(int pxW, int pxH){
    this("", EcConst.C_ID_IGNORE, true, pxW, pxH);
  }//++!
  
  /**
   * @param pxKey will get passed to super directly
   * @param pxVert could be anything
   * @param pxW greater than zero or nothing
   * @param pxH greater than zero or nothing
   */
  public EcGauge(String pxKey, boolean pxVert, int pxW, int pxH){
    this(pxKey, EcConst.C_ID_IGNORE, pxVert, pxW, pxH);
  }//++!
  
  /**
   * will have no identical id.<br>
   * @param pxKey will get passed to super directly
   * @param pxW greater than zero or nothing
   * @param pxH greater than zero or nothing
   */
  public EcGauge(String pxKey, int pxW, int pxH){
    this(pxKey, EcConst.C_ID_IGNORE, true, pxW, pxH);
  }//++!
  
  /**
   * @param pxKey will get passed to super directly
   * @param pxID will get passed to super directly and also effect enability
   * @param vert could be anything
   */
  public EcGauge(String pxKey, int pxID, boolean vert){
    this(pxKey, pxID, vert, -1, -1);
  }//++!
  
  /**
   * @param pxKey will get passed to super directly
   * @param vert could be anything
   */
  public EcGauge(String pxKey, boolean vert){
    this(pxKey, EcConst.C_ID_IGNORE, vert, -1, -1);
  }//++!
  
  /**
   * is vertical by default.<br>
   * has stroke by default.<br>
   * @param pxKey will get passed to super directly
   * @param pxID will get passed to super directly and also effect enability
   */
  public EcGauge(String pxKey, int pxID){
    this(pxKey, pxID, true, -1, -1);
  }//++!
  
  /**
   * inherited default.<br>
   * is vertical by default.<br>
   * has stroke by default.<br>
   * @param pxKey will get passed to super directly
   */
  public EcGauge(String pxKey){
    super(pxKey);
    ccSetTextAlign('x');
    ccSetIsEnabled(false);
  }//..!
  
  /**
   * inherited default.<br>
   * has stroke by default.<br>
   * is vertical by default.<br>
   */
  public EcGauge(){
    super();
    ccSetTextAlign('x');
    ccSetIsEnabled(false);
  }//++!
  
  //===
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate() {
    
    if(!ccIsVisible()){return;}
    
    //-- pre
    int lpLength=ssToScaledPixLength(cmIsVertical?cmH:cmW);
    ccApplyClickedValue();
    
    //-- draw
    int lpAdjust=0;
    if(cmHasStroke){
      pbOwner.stroke(cmStrokeColor);
      lpAdjust=1;
      {lpLength-=1;if(lpLength<1){lpLength=1;}}//..we will see
    }//..?
    pbOwner.fill(cmBackColor);
    pbOwner.rect(cmX,cmY,cmW-lpAdjust,cmH-lpAdjust);
    ccActFill();
    if(cmIsVertical){
      pbOwner.rect(cmX,cmY+cmH,cmW-lpAdjust,-1*lpLength);
    }else{
      pbOwner.rect(cmX,cmY,lpLength,cmH-lpAdjust);
    }//..?
    if(cmHasStroke){pbOwner.noStroke();}
    drawName(cmNameColor);
    drawText(cmTextColor);
  
  }//++~
  
  //===
  
  /**
   * <b>DOUBLE ALIASING PROPORTION METHOD OF NUMERIC UTILITY</b><br>
   * @param pxZeroToOne : 0.00 - 1.00f
   */
  public final void ccSetProportion(float pxZeroToOne){
    cmContentValue=VcNumericUtility.ccProportion(pxZeroToOne);
  }//++<
  
  /**
   * the gauge will be full as this value equals 255.<br>
   * @param pxByte : will get masked to 0-255
   */
  public final void ccSetProportion(int pxByte){
    cmContentValue=pxByte&C_VAL_MASK;
  }//++<
  
  /**
   * like if you wanna the value of 50%, you can pass (50,100).<br>
   * <b>DOUBLE ALIASING PROPORTION METHOD OF NUMERIC UTILITY</b><br>
   * @param pxVal should be less than span but this do NOT check
   * @param pxSpan if passed zero this will NOT throw or print anything
   */
  public final void ccSetProportion(int pxVal, int pxSpan){
    cmContentValue=VcNumericUtility.ccProportion
      (VcNumericUtility.ccProportion(pxVal, pxSpan));
  }//++<
  
  /**
   * 
   * @param cmVal : has stroke?
   */
  public final void ccSetHasStroke(boolean cmVal){
    cmHasStroke=cmVal;
  }//++<
  
  /**
   * 
   * @param cmVal : is vertical?
   */
  public final void ccSetIsVertical(boolean cmVal){
    cmIsVertical=cmVal;
  }//++<
  
  /**
   * only for the backend color set <br>
   * the content color can be set via EcElement::ccSetColor()
   * @param pxBack #
   * @param pxStroke #
   */
  public final void ccSetGaugeColor(int pxBack, int pxStroke){
    cmBackColor=pxBack;
    cmStrokeColor=pxStroke;
  }//++<
  
  //===
  
  /**
   * for gauge head calculation.<br>
   * inner use only.<br>
   * @param pxLength width or height in pix
   * @return length * value / span
   */
  protected final int ssToScaledPixLength(int pxLength){
    return pxLength*cmContentValue/C_VAL_MASK;
  }//+++
  
  /**
   * mouse location will affect the content value.<br>
   */
  protected final void ccApplyClickedValue(){
    if(!ccIsVisible()){return;}
    if(!ccIsMousePressed()){return;}
    cmContentValue=cmIsVertical?
       (C_VAL_MASK-ccGetMouseOffsetY()*C_VAL_MASK/cmH)
      :(           ccGetMouseOffsetX()*C_VAL_MASK/cmW);
  }//+++
  
  //===
  
  /**
   * <pre>
   * you have to note that the value is heavily depends on
   *   the PApplet coordinate system.
   * it is pretty anti-intuitive when oriented in virtical.
   * try ccGetReversedValue().
   * </pre>
   * @return # 0-255
   */
  public final int ccGetContentValue(){
    return cmContentValue&C_VAL_MASK;
  }//++>
  
  /**
   * <pre>
   * for orientation issues.
   * </pre>
   * @return # 0-255
   */
  public final int ccGetReversedValue(){
    return 0xFF-ccGetContentValue();
  }//++>

  /**
   * additionally orientation and stroke mode will get fetched.<br>
   * @param pxTarget #
   */
  public void ccSetupStyle(EcGauge pxTarget) {
    super.ccSetupStyle(pxTarget);
    cmIsVertical=pxTarget.cmIsVertical;
    cmHasStroke=pxTarget.cmHasStroke;
  }//++>
  
}//***eof

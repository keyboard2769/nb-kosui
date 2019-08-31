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

import static processing.core.PApplet.ceil;

/**
 * a gauge can be filled.<br>
 * some one call this "bar" also.<br>
 */
public class EcGauge extends EcElement {
  
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
   * between 0-127
   */
  protected int cmContentValue = 32;
  
  //==
  
  /**
   * 
   * @param pxZeroToOne : 0.00 - 1.00f
   */
  public final void ccSetPercentage(float pxZeroToOne){
    cmContentValue=ceil(127f*pxZeroToOne)&127;
  }//+++
  
  /**
   * the gauge will be full as this value equals 127
   * @param pxHalfByte : 0-127
   */
  public final void ccSetPercentage(int pxHalfByte)
    {cmContentValue=pxHalfByte&127;}//+++
  
  /**
   * like if you wanna the value of 50%,
   * you can pass (50,100).
   * @param pxVal should be less than span but this do NOT check
   * @param pxSpan if passed zero this will NOT throw or print anything
   */
  public final void ccSetPercentage(int pxVal, int pxSpan){
    if(pxSpan==0 || pxVal==0){return;}
    cmContentValue=(pxVal*127/pxSpan)&127;
  }//+++
  
  /**
   * 
   * @param cmVal : has stroke?
   */
  public final void ccSetHasStroke(boolean cmVal){cmHasStroke=cmVal;}//+++
  
  /**
   * 
   * @param cmVal : is vertical?
   */
  public final void ccSetIsVertical(boolean cmVal){cmIsVertical=cmVal;}//+++
  
  /**
   * only for the backend color set <br>
   * the content color can be set via EcElement::ccSetColor()
   * @param pxBack #
   * @param pxStroke #
   */
  public final void ccSetGaugeColor(int pxBack, int pxStroke){
    cmBackColor=pxBack;
    cmStrokeColor=pxStroke;
  }//+++
  
  //==
  
  /**
   * {@inheritDoc }
   */
  @Override public void ccUpdate() {
    
    int lpLength=ccTellScale(cmIsVertical?cmH:cmW);
    
    if(cmHasStroke){pbOwner.stroke(cmStrokeColor);}
    
    pbOwner.fill(cmBackColor);
    pbOwner.rect(cmX,cmY,cmW,cmH);
    
    ccActFill();
    if(cmIsVertical){pbOwner.rect(cmX,cmY+cmH,cmW,-lpLength);}
    else{pbOwner.rect(cmX,cmY,lpLength,cmH);}
    
    if(cmHasStroke){pbOwner.noStroke();}
    
    drawName(cmNameColor);
  
  }//+++
  
  //== teller
  
  /**
   * ##
   * @param pxVal this value has nothing to do with this
   * @return inputed_value*current_gauge/127 
   */
  public final int ccTellScale(int pxVal)
    {return pxVal*cmContentValue/127;}//+++
  
  //==
  
  /**
   * 
   * @return between 0-127
   */
  public final int ccGetContentValue()
    {return cmContentValue;}//+++

}//***eof
